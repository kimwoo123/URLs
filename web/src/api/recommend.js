import { api } from "src/boot/axios";

export function recommendation(data, id) {
  return api.get(`/recommend/${id}`, data)
}
