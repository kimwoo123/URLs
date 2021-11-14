window.showHighlighterCursor = false;

document.addEventListener('mouseup', () => {
  if (!window.showHighlighterCursor) return;

  const selection = window.getSelection();
  const selectionString = selection.toString();

  if (selectionString) {
    chrome.runtime.sendMessage({action: 'highlight'});
    // send background signal
  }
});
