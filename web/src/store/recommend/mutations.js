export function setTag(state, result) {
  state.tag = result
}

export function setRecommendTag(state, result) {
  state.recommendTag = result
}

export function resetRecommendTag(state) {
  state.recommendTag = []
}

export function setRecommendUrls(state, urls) {
  state.recommendUrls = urls
}

export function setSearchResult(state, urls) {
  state.searchResult = urls
}