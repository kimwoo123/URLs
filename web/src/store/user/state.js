import {
	getUserFromCookie,
  getAuthFromCookie,
  getUseridFromCookie,
  getUserAvatarFromCookie,
} from 'src/utils/cookies'

export default function () {
  return {
    username: getUserFromCookie() || '',
    token: getAuthFromCookie() || '',
    userid: getUseridFromCookie() || '',
    avatar: getUserAvatarFromCookie() || '',
  }
}