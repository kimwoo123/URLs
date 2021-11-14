/* global highlightId */

function show() {
  if (highlightId !== undefined) {
    const highlightEl = $(`[data-highlight-id="${highlightId}"]`);
    if (highlightEl) {
      highlightEl.scrollIntoViewIfNeeded(true);
      const boundingRect = highlightEl.getBoundingClientRect();
      onHighlightMouseEnterOrClick({
        type: 'click',
        target: highlightEl,
        clientX: boundingRect.left + boundingRect.width / 2,
      });
    }
  }
}

show();
