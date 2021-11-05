import { api } from "src/boot/axios";

export function recommendation() {
  return api.get('/folder/me')
}
