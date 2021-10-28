from fastapi import Depends, APIRouter, HTTPException, status
from models.folder import User, UserIn, Url, UrlIn, UrlInDB, FolderIn, FolderInDB, FolderOut
from models.memo import MemoIn, MemoInDB, MemoGroup
from config.db import db
from serializers.common import serializeDict, serializeList
from fastapi.encoders import jsonable_encoder
# from fastapi.responses import JSONResponse
from bson import ObjectId
from pprint import pprint
from pymongo import ReturnDocument
from .token import get_current_user


folder_user = APIRouter()


@folder_user.post('/folder/{id}/user', summary="폴더 유저 생성", response_model=FolderOut)
async def create_folder_user(id, user_in: UserIn):
    # 유저 찾기
    target_user = db.user.find_one({"email": user_in.email})
    if target_user is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {user_in.email} not found")
    user = User(**target_user, permission=user_in.permission)
    # push
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id)}, {"$push": {"users": dict(user)}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_user.put('/folder/{id}/user', summary="폴더 유저 권한 변경", response_model=FolderOut)
async def update_folder_user(id, user_in: UserIn):
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id), "users.email": user_in.email},
        {"$set": {"users.$.permission": user_in.permission}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_user.delete('/folder/{id}/user', summary="폴더 유저 삭제", response_model=FolderOut)
async def delete_folder_user(id, user_in: UserIn):
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id)},
        {"$pull": {"users": {"email": user_in.email}}},
        return_document=ReturnDocument.AFTER
    )
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")

