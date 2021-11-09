/*
export function someAction (context) {
}
*/
export async function searchTag({ commit }, tagData) {
  const searchTag = {
    'Tag': tagData,
  }
  commit('setRecommend', userData.token);
}