export function setTag(state, result) {
  state.tag = result
}

export function setRecommendTag(state, result) {
  state.recommendTag = result
}

export function resetRecommendTag(state) {
  state.recommendTag = []
}
