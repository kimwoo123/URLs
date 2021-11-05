import {
	getUserFromCookie,
  getAuthFromCookie,
  getUseridFromCookie,
} from 'src/utils/cookies'

export default function () {
  return {
    username: getUserFromCookie() || '',
    token: getAuthFromCookie() || '',
    userid: getUseridFromCookie() || '',
  }
}