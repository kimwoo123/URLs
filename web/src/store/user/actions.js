import { auth } from 'src/api/index'
import {
	saveAuthToCookie,
  saveUserToCookie,
  saveUseridToCookie,
  saveUserAvatarToCookie,
  deleteCookie
} from 'src/utils/cookies'

export async function LOGIN({ commit }, userData) {
  const loginData = {
    'email': userData.useremail,
    'nickname': userData.nickname,
    'avatar': userData.avatar,
  }
  commit('setToken', userData.token);
  commit('setUserName', userData.nickname)
  commit('setUserAvatar', userData.avatar)
  saveAuthToCookie(userData.token)
  saveUserToCookie(userData.nickname)
  saveUserAvatarToCookie(userData.avatar)
  const { data } = await auth.user(loginData);
  console.log(data)
  commit('setUserid', data['_id'])
  saveUseridToCookie(data['_id'])
}

export async function LOGOUT({ commit }) {
  commit('clearUsername')
  commit('clearToken')
  commit('clearUserid')
  commit('clearUserAvatar')
  deleteCookie('til_auth')
  deleteCookie('til_user')
  deleteCookie('til_userid')
  deleteCookie('til_avatar')
}