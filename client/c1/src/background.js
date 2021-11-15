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

async function serverCheck() {
  const baseURL = 'http://k5b201.p.ssafy.io:4000';
  const config = {
    method: 'GET',
    headers: {'Content-Type': 'application/json;'},
  };
  return fetch(`${baseURL}/user`, config)
    .then(result => result)
    .catch(error => error);
}

// 백그라운드 로직 처리 => 비동기적인 상황(promise)
chrome.runtime.onMessage.addListener(async (request, sender, sendResponse) => {
  if (request.action) {
    if (request.action === 'highlight') {
      chrome.tabs
        .executeScript({file: 'contentScripts/highlight.js'})
        .then(result => result);
    } else if (request.action === 'serverCheck') {
      const response = await serverCheck();
      const status = response.status === 200 ? 'Live' : 'Dead';
      alert(`Server status${status}`);
      sendResponse({result: response.status === 200});
    } else if (request.action === 'tokenCheck') {
      const token = await getFromStorage('token');
      alert(token);
      if (token) {
        sendResponse({result: true, token});
      } else {
        sendResponse({result: false, token: ''});
      }
    } else if (request.action === 'error') {
      alert('서버와 통신이 잡히지 않아 공유하기가 힘듭니다.');
    }
  }
});
