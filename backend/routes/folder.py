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

folder = APIRouter()

# 내 모든 폴더 조회 만들기


@folder.get('/folder', summary="내 모든 폴더 조회 | 구현X", response_model=FolderOut)
async def find_all_folder():
    pass


@folder.get('/folder/{id}', summary="폴더 상세 조회", response_model=FolderOut)
async def find_one_folder(id):
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder.post('/folder', summary="단일 폴더 생성", response_model=FolderOut)
async def create_folder(folder_in: FolderIn, user: User = Depends(get_current_user)):
    result = db.folder.insert_one(jsonable_encoder(FolderInDB(**folder_in.dict(), users=[User(**user)])))
    new_folder = db.folder.find_one({"_id": ObjectId(result.inserted_id)})
    return serializeDict(new_folder)


@folder.put('/folder/{id}', summary="폴더명 변경", response_model=FolderOut)
async def update_folder(id, folder_in: FolderIn):
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id)}, {"$set": {"folder_name": folder_in.folder_name}}, 
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder.delete('/folder/{id}', summary="폴더 삭제", response_model=FolderOut)
async def delete_folder(id):
    folder = db.folder.find_one_and_delete({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")
