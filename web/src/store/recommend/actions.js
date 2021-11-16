import { search } from "src/api/index";

export async function SEARCH_TAG({ commit }, queryData) {
  await search.searchTag(queryData).then(async result => {
    await commit("setTag", result.data);
  });
}

export async function RECOMMEND_TAG({ commit }, recommendData) {
  await search.recommendTag(recommendData)
  .then(async (result) => {
    recommendData.tags = result.data
    await commit('setRecommendTag', result.data)
  })
}

export async function DELETE_RECOMMEND_TAG({ commit }) {
  await commit('resetRecommendTag')
}
