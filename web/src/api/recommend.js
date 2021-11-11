import { api } from "src/boot/axios";

// 해당 url의 tags 추천
export function recommendUrlTags(urlData) {
  return api.get('/tags', urlData)
}

// 전체 url DB 속 url 있는지 조회
export function recommendUrlGet(urlData) {
  return api.get('/recommend', urlData)
}

// 추천을 위한 전체 url DB 속 단일 URL 수정
export function recommendUrlPut(urlData) {
  return api.put('/recommend', urlData)
}

// 추천을 위한 전체 url DB에 신규 url 추가
export function recommendUrlCreate(urlData) {
  return api.post('/recommend', urlData)
}

// url 추천
export function recommendUrl(data, id) {
  return api.get(`/recommend/${id}`, data)
}
