import { urls } from 'src/api/index'

export async function GET_FOLDER({ commit }) {
  await urls.folderAll()
    .then(async(result) => {
      console.log(result.data)
      const folders = result.data
      await commit('setFolder', folders)
    })
}

export async function GET_ALL_URL({ commit }) {
  await urls.urlFindAll()
    .then(async (result) => {
      console.log('urls 결과', result.data)
    }) 
}

export async function GET_FOLDER_ULR({ commit }, folderId) {
  await urls.folderDetail(folderId)
    .then(async (result) => {
      console.log('urls 결과', result.data.urls)
      const urls = result.data.urls
      await commit('setUrl', urls)
    })
    .catch(async (error) => {
      console.log(error)
      await commit('setUrl', [])
    })
}