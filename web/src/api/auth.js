import { api } from "src/boot/axios";

export function loginToken(loginData) {
  return api.post('/user', loginData)
}
