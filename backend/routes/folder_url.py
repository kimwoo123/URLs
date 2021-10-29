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

folder_url = APIRouter()


# 폴더 url 관리

@folder_url.post('/folder/{id}/url', summary="폴더 url 생성")
async def create_folder_url(id, url_in: UrlIn, user: User = Depends(get_current_user)):
    oid = ObjectId()
    url = UrlInDB(
        **url_in.dict(), 
        added_by=User(**user),
        memo_id=oid
        )
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id)}, {"$push": {"urls": jsonable_encoder(url)}},
        return_document=ReturnDocument.AFTER
    )
    db.memo.insert(jsonable_encoder(MemoGroup(_id=oid)))
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_url.put('/folder/{id}/url', summary="폴더 url 수정")
async def update_folder_url(id, url_in: UrlIn, user: User = Depends(get_current_user)):
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id), "urls.url": url_in.url}, 
        {"$set": {"urls.$.thumbnail": url_in.thumbnail, "urls.$.tags": url_in.tags}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_url.delete('/folder/{id}/url', summary="폴더 url 삭제")
async def delete_folder_url(id, url_in: UrlIn):
    tmp = db.folder.find_one(
        {"_id": ObjectId(id), "urls.url": url_in.url},
        {"urls.$":1}
    )
    memo_id = tmp["urls"][0]["memo_id"]
    
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id)},
        {"$pull": {"urls": {"url": url_in.url}}}, 
        return_document=ReturnDocument.AFTER
    )
    db.memo.delete_one({"_id": memo_id})
    
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_url.put('/folder/{id}/url/tag', summary="폴더 url 태그 추가 및 삭제 | 구현X")
async def update_folder_url_tag(id):
    pass


@folder_url.put('/folder/{id}/url/thumbnail', summary="폴더 url 썸네일 변경 | 구현X")
async def update_folder_url_thumbnail(id):
    pass
