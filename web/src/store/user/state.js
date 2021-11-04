import {
	getUserFromCookie,
  getAuthFromCookie,
} from 'src/utils/cookies'

export default function () {
  return {
    useremail: getUserFromCookie() || '',
    token: getAuthFromCookie() || '',
  }
}