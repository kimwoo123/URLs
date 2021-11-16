import { urls } from 'src/api/index'

export async function GET_FOLDER({ commit }) {
  await urls.folderAll()
    .then(async(result) => {
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

export async function GET_FOLDER_URL({ commit }, folderId) {
  await urls.folderDetail(folderId)
    .then(async (result) => {
      await commit('setUrl', result.data.urls)
      await commit('setFolderNow', result.data)
    })
    .catch(async (error) => {
      console.log(error)
      await commit('setUrl', [])
      await commit('setFolderNow', {})
    })
}

export async function CREATE_FOLDER({ commit, dispatch }, folderData) {
  await urls.folderCreate(folderData)
    .then((result) => {
      dispatch('GET_FOLDER')
      commit('setUrl', result.data.urls)
      commit('setFolderNow', result.data)
    })
}

export async function PUT_FOLDER_NAME({ commit, dispatch }, folderData) {
  await urls.folderPut(folderData)
    .then(async (result) => {
      dispatch('GET_FOLDER')
      commit('setFolderNow', result.data)
    })
}

export async function DELETE_FOLDER({ commit, dispatch }, folderId) {
  await urls.folderDelete(folderId)
    .then(async (result) => {
      console.log(result)
      dispatch('GET_FOLDER')
      commit('setUrl', [])
      commit('setFolderNow', {})
    })
}

export function OPEN_MEMO({ commit }) {
  commit('openMemo')
}

export function CLOSE_MEMO({ commit }) {
  commit('closeMemo')
}

export async function GET_URL_MEMO({ commit }, memoId) {
  await urls.memoAll(memoId)
    .then(async (result) => {
      commit('setSelectedMemoId', memoId)
      commit('setUrlMemo', result.data.memos)
    })
}

export async function CREATE_URL_MEMO({ commit }, memoData) {
  await urls.memoCreate(memoData)
    .then(async (result) => {
      commit('setUrlMemo', result.data.memos)
    })
}

export async function PUT_URL_MEMO({ commit }, memoData) {
  await urls.memoPut(memoData)
    .then(async (result) => {
      commit('setUrlMemo', result.data.memos)
    })
    .catch(error => {
      console.log('에러다')
      console.log(error)
    })
}

export async function DELETE_URL_MEMO({ commit }, memoData) {
  await urls.memoDelete(memoData)
    .then(async (result) => {
      commit('setUrlMemo', result.data.memos)
    })
}

export async function CREATE_URL({ commit }, urlData) {
  await urls.urlCreate(urlData.folderId, urlData)
  .then(async (result) => {
    commit('setUrl', result.data)
  })
}
