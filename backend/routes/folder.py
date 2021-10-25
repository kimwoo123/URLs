from fastapi import APIRouter, HTTPException, status
from typing import List
 
from models.folder import Folder, User, Highlight, Url, FolderIn
from config.db import db
from schemas.user import serializeDict, serializeList
from bson import ObjectId
from pprint import pprint

folder = APIRouter()
url = APIRouter()


@folder.get('/folder/{id}', summary="폴더 상세 조회")
async def find_one_folder(id):
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder.post('/folder', summary="폴더 생성")
async def create_folder(folder_in: FolderIn):
    folder = Folder(
        folder_name = folder_in.folder_name,
        users = [folder_in.user],
    )
    folder_inDB = dict(folder)
    folder_inDB["users"] = [dict(folder_inDB["users"][0])]
    db.folder.insert_one(folder_inDB)
    return serializeList(db.folder.find())


@folder.put('/folder/{id}', summary="폴더명 변경")
async def update_folder(id, folder_name):
    db.folder.find_one_and_update({"_id": ObjectId(id)}, {"$set": {"folder_name": folder_name}})
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder.delete('/folder/{id}', summary="폴더 삭제")
async def delete_folder(id):
    folder = db.folder.find_one_and_delete({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")



# 폴더 유저 관리

@folder.post('/folder/{id}/user', summary="폴더 유저 생성")
async def create_folder_user(folder_id):
    pass

@folder.put('/folder/{id}/user', summary="폴더 유저 권한 변경")
async def update_folder_user(folder_id):
    pass

@folder.delete('/folder/{id}/user', summary="폴더 유저 삭제")
async def delete_folder_user(folder_id):
    pass



# 폴더 url 관리

@url.post('/folder/{id}/url', summary="폴더 url 생성")
async def create_folder_url(folder_id):
    pass

@url.put('/folder/{id}/url', summary="폴더 url 수정")
async def update_folder_user(folder_id):
    pass

@url.delete('/folder/{id}/url', summary="폴더 url 삭제")
async def delete_folder_user(folder_id):
    pass


@url.post('/folder/{id}/url', summary="폴더 태그 추가 및 삭제")
async def update_folder_user(folder_id, tag):
    pass


@url.put('/folder/{id}/url/', summary="폴더 하이라이트 생성")
async def update_folder_user(folder_id, highlight):
    pass

@url.put('/folder/{id}/url', summary="폴더 하이라이트 수정")
async def update_folder_user(folder_id, highlight):
    pass