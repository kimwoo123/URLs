function highlightText() {
  chrome.tabs
    .executeScript({file: 'contentScripts/highlight.js'})
    .then(result => result);
}

function highlightTextFromContext() {
  highlightText();
}

function toggleHighlighterCursor() {
  chrome.tabs
    .executeScript({
      file: 'contentScripts/toggleHighlighterCursor.js',
    })
    .then(result => result);
}

function toggleHighlighterCursorFromContext() {
  toggleHighlighterCursor();
}

chrome.contextMenus.create({
  title: 'Urls에서 관리하기',
  onclick: highlightTextFromContext,
  contexts: ['selection'],
});
chrome.contextMenus.create({
  title: 'Toggle Cursor',
  onclick: toggleHighlighterCursorFromContext,
});

chrome.storage.sync.get('color', values => {
  const color = values.color ? values.color : 'yellow';
  chrome.contextMenus.update(color, {checked: true});
});

chrome.runtime.onMessage.addListener((request, _sender, _sendResponse) => {
  if (request.action && request.action === 'highlight') {
    highlightText();
  }
});
