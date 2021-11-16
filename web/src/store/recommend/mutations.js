export function setTag(state, result) {
  console.log(result, 'reco mutation')
  state.tag = result
}

export function setRecommendTag(state, result) {
  console.log(result, 'reco mutation')
  state.recommendTag = result
}

export function resetRecommendTag(state) {
  state.recommendTag = []
}