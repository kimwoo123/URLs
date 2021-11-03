import { api } from "src/boot/axios";

function LoginToken(userData) {
  return api.post('/token', userData)
}

export {
  LoginToken,
}