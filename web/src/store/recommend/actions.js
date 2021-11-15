import { searchTag } from 'src/api/search'

export async function SEARCH_TAG({ commit }, queryData) {
  await searchTag(queryData)
  .then(async (result) => {
    console.log(result, 'action')
    await commit('setTag', result.data);
  })
}