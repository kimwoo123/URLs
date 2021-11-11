import { api } from "src/boot/axios";

export function folderMe() {
  return api.get('/folder/me')
}

export function folderUrlMe() {
  return api.get('/folder/url/me')
}

export function folderId(folderId) {
  return api.get(`/folder/${folderId}`)
}