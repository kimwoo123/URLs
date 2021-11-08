import { api } from "src/boot/axios";

export function user(loginData) {
  return api.post('/user', loginData)
}

export function tokenMe() {
  console.log('토큰미')
  return api.get('/token/me')
}
