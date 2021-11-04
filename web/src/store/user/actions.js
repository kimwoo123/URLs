import { loginToken } from 'src/api/auth'
import {
	saveAuthToCookie,
	saveUserToCookie,
	getUserFromCookie,
	deleteCookie,
} from 'src/utils/cookies'

export async function LOGIN({ commit }, userData) {
  const loginData = {
    'username': userData.useremail,
    'password': userData.uid,
  }
  console.log(userData.token);
  commit('setToken', userData.token);
  commit('setUseremail', userData.useremail)
  saveAuthToCookie(userData.token)
  saveUserToCookie(userData.token)
  const { data } = await loginToken(loginData);
  return data;
}
