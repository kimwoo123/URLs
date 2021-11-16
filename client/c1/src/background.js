import axios from 'axios';

const baseURL = 'http://k5b201.p.ssafy.io:4000';

chrome.contextMenus.create({
  title: 'Urls에서 관리하기',
  onclick: () => {
    chrome.tabs
      .executeScript({file: 'contentScripts/highlight.js'})
      .then(result => result)
      .catch(error => error);
  },
  contexts: ['selection'],
});

chrome.contextMenus.create({
  title: '하이라이트를 클릭해주세요!!',
  onclick: () => {
    chrome.tabs
      .executeScript({
        file: 'contentScripts/toggleHighlighterCursor.js',
      })
      .then(result => result)
      .catch(error => error);
  },
});

chrome.storage.sync.get('color', values => {
  const color = values.color || 'yellow';
  chrome.contextMenus.update(color, {checked: true});
});

async function getFromStorage(key) {
  return new Promise((resolve, reject) => {
    chrome.storage.sync.get(key, resolve);
  })
    .then(result => {
      if (key == null) return result;
      return result[key];
    })
    .catch(error => error);
}

function providePostConfig(token, payload) {
  return {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;',
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(payload),
  };
}

function provideGetConfig(token) {
  return {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;',
      Authorization: `Bearer ${token}`,
    },
  };
}

async function serverCheck(token) {
  return fetch(`${baseURL}/user`, provideGetConfig(token))
    .then(result => result)
    .catch(error => error);
}

async function newUrl(token, basic, url) {
  return fetch(
    `${baseURL}/folder/${basic}/url`,
    providePostConfig(token, {
      url,
      thumbnail: '',
      tags: [],
    }),
  )
    .then(result => result)
    .catch(error => error);
}

async function newMemo(token, basic, message) {
  return fetch(
    `${baseURL}/folder/${basic}/url`,
    providePostConfig(token, {
      highlight: message,
      content: message,
    }),
  )
    .then(result => result)
    .catch(error => error);
}

async function inject(token, folderId, payload) {
  return fetch(
    `${baseURL}/folder/${folderId}/url`,
    providePostConfig(token, payload),
  );
}

// 백그라운드 로직 처리 => 비동기적인 상황(promise)
chrome.runtime.onMessage.addListener(async (request, sender, sendResponse) => {
  if (request.action) {
    const token = await getFromStorage('token');
    const basic = await getFromStorage('basic');
    if (request.action === 'highlight') {
      chrome.tabs
        .executeScript({file: 'contentScripts/highlight.js'})
        .then(result => result);
    } else if (request.action === 'serverCheck') {
      const response = await serverCheck(token);
      alert(`Server status ${response.status === 200 ? 'Live' : 'Dead'}`);
      sendResponse({result: response.status === 200});
    } else if (request.action === 'tokenCheck') {
      alert(token);
      if (token) {
        sendResponse({result: true, token});
      } else {
        sendResponse({result: false, token: ''});
      }
    } else if (request.action === 'share') {
      alert('시작');
      const response = await inject(token, basic, {
        url: request.url,
        tags: [],
      });
      const result = response.status === 200;
      alert(result);
      sendResponse({result});
    } else if (request.action === 'newMemo') {
      alert(request.highlightText);
    }
  }
});
