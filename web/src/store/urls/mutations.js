
export function setFolder(state, folders) {
  state.folders = folders
}

export function setUrl(state, urls) {
  state.urls = urls
}

export function openMemo(state) {
  state.urlMemoOpen = true
}

export function closeMemo(state) {
  state.urlMemoOpen = false
}

export function setUrlMemo(state, memos) {
  state.memos = memos
}

export function setSelectedMemoId(state, memoId) {
  state.selectedMemoId = memoId
}