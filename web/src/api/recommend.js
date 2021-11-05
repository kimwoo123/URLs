import { api } from "src/boot/axios";

function recommendation(data, id) {
  return api.post(`recommend/${id}`, data)
}


export {
  recommendation,
}