import { api } from "src/boot/axios";

export function search() {
  return api.get('/search')
}