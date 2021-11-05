import { api } from "src/boot/axios";

function loginToken(loginData) {
  return api.post('/user', loginData)
}

export {
  loginToken,
}