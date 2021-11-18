
export function folderUrls(state) {
  return state.urls
}

export function isUrlMemoOpen(state) {
  return state.urlMemoOpen
}

export function folderNow(state) {
  return state.folderNow
}

export function permissionNow(state, getters, rootState, rootGetters) {
  if (getters.folderNow._id === '') {
    return 2
  } else {
    const myEmail = rootState.user.useremail
    const findUser = (user) => {
      return user.email === myEmail
    }
    const result = getters.folderNow.users.find(findUser)

    console.log(result.permission)

    if (result.permission == undefined) {
      return -1
    } else {
      return result.permission
    }
  }
}

export function willDeleteURL(state) {
  return state.willDeleteURL
}