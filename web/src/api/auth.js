import { api } from "src/boot/axios";

function loginToken(loginData) {
  console.log('loginData는', loginData)
  return api.post('/user', loginData)
}

export {
  loginToken,
}