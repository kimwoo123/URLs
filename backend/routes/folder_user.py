from fastapi import Depends, APIRouter, HTTPException, status
from models.folder import User, UserIn, FolderOut
from config.db import db
from serializers.common import serializeDict, serializeList
from fastapi.encoders import jsonable_encoder
# from fastapi.responses import JSONResponse
from bson import ObjectId
from pprint import pprint
from pymongo import ReturnDocument
from .token import get_current_user


folder_user = APIRouter()


@folder_user.post('/folder/{folder_id}/user', summary="폴더 유저 생성", response_model=FolderOut)
async def create_folder_user(folder_id, user_in: UserIn):
    # 유저 찾기
    target_user = db.user.find_one({"email": user_in.email})
    if target_user is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {user_in.email} not found")
    user = User(**target_user, permission=user_in.permission)

    # 폴더에 유저 push
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id)}, {"$push": {"users": dict(user)}},
        return_document=ReturnDocument.AFTER
    )
    # 유저의 폴더에 폴더 push
    taget_user_folder = {
      "folder_id": folder["_id"],
      "folder_name": folder["folder_name"],
      "shared": folder["shared"]
    }
    db.user.find_one_and_update({"_id": ObjectId(target_user["_id"])}, {"$push": {"folders": taget_user_folder}})

    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")


@folder_user.put('/folder/{folder_id}/user', summary="폴더 유저 권한 변경", response_model=FolderOut)
async def update_folder_user(folder_id, user_in: UserIn):
    # 폴더에 유저 권한 변경
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id), "users.email": user_in.email},
        {"$set": {"users.$.permission": user_in.permission}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")


@folder_user.delete('/folder/{folder_id}/user', summary="폴더 유저 삭제", response_model=FolderOut)
async def delete_folder_user(folder_id, user_in: UserIn):
    # 폴더에서 유저 삭제
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id)},
        {"$pull": {"users": {"email": user_in.email}}},
        return_document=ReturnDocument.AFTER
    )
    # 유저의 폴더에서 폴더 삭제
    db.user.find_one_and_update(
        {"email": user_in.email},
        {"$pull": {"folders": {"folder_id": ObjectId(folder_id)}}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")

