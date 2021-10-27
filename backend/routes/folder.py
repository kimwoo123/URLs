from fastapi import APIRouter, HTTPException, status
from models.folder import Folder, User, Highlight, Url, FolderIn
from config.db import db
from serializers.common import serializeDict, serializeList
from bson import ObjectId
from pprint import pprint
from pymongo import ReturnDocument

folder = APIRouter()
folder_url = APIRouter()
folder_user = APIRouter()

@folder.get('/folder/{id}', summary="폴더 상세 조회")
async def find_one_folder(id):
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


# 추후 체크
@folder.post('/folder', summary="폴더 생성")
async def create_folder(folder_in: FolderIn):
    folder = {
        "folder_name": folder_in.folder_name,
        "users": [dict(folder_in.user)],
        "urls": []
    }
    result = db.folder.insert_one(folder)
    new_folder = db.folder.find_one({"_id": ObjectId(result.inserted_id)})
    return serializeDict(new_folder)


@folder.put('/folder/{id}', summary="폴더명 변경")
async def update_folder(id, folder_name):
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id)}, {"$set": {"folder_name": folder_name}}, 
        return_document=ReturnDocument.AFTER
    )
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

@folder_user.post('/folder/{id}/user', summary="폴더 유저 생성")
async def create_folder_user(id, user: User):
    db.folder.update_one({"_id": ObjectId(id)}, {"$push": {"users": dict(user)}})
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_user.put('/folder/{id}/user', summary="폴더 유저 권한 변경")
async def update_folder_user(id, email, permission: int):
    db.folder.update_one(
        {"_id": ObjectId(id), "users.email": email},
        {"$set": {"users.$.permission": permission}}
    )
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")

@folder_user.delete('/folder/{id}/user', summary="폴더 유저 삭제")
async def delete_folder_user(id, email):
    db.folder.update_one(
        {"_id": ObjectId(id)},
        {"$pull": {"users": {"email": email}}}
    )
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")



# 폴더 url 관리

@folder_url.post('/folder/{id}/url', summary="폴더 url 생성")
async def create_folder_url(folder_id):
    pass

@folder_url.put('/folder/{id}/url', summary="폴더 url 수정")
async def update_folder_user(folder_id):
    pass

@folder_url.delete('/folder/{id}/url', summary="폴더 url 삭제")
async def delete_folder_user(folder_id):
    pass


@folder_url.post('/folder/{id}/url', summary="폴더 태그 추가 및 삭제")
async def update_folder_user(folder_id, tag):
    pass


@folder_url.put('/folder/{id}/url/', summary="폴더 하이라이트 생성")
async def update_folder_user(folder_id, highlight):
    pass

@folder_url.put('/folder/{id}/url', summary="폴더 하이라이트 수정")
async def update_folder_user(folder_id, highlight):
    pass