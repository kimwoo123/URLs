export function setTag(state, result) {
  console.log(result, 'mutation')
  state.recommendTag = result;
}