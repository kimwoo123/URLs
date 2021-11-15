import { api } from "src/boot/axios";

export function searchTag(queryData) {
  return api.get(`/search?searchText=${queryData.searchText}&folder=${queryData.folderId}`)
}