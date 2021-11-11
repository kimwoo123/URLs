import { api } from "src/boot/axios";

export function user(loginData) {
  return api.post('/user', loginData)
}

export function tokenMe() {
  return api.get('/token/me')
}
