from fastapi import Depends, APIRouter, HTTPException, status
from models.folder import User, UserIn, Url, UrlIn, UrlInDB, FolderIn, FolderInDB, FolderOut
from models.memo import MemoIn, MemoInDB, MemoGroup, Memos
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

@folder_url.get('/folder/url', summary="내 모든 폴더에서 특정 url 검색 | 구현 X")
async def find_one_folder_url(url, user: User = Depends(get_current_user)):
    pass
    # folder = db.folder.find_one(
    #     {"_id": ObjectId(id), "urls.url": url},
    #     {"urls.$":1}
    # )
    # if folder is not None:
    #     return serializeDict(folder)
    # raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_url.get('/folder/{id}/url', summary="폴더 내 특정 url 검색")
async def find_one_folder_url(id, url):
    folder = db.folder.find_one(
        {"_id": ObjectId(id), "urls.url": url},
        {"urls.$":1}
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_url.post('/folder/{id}/url', summary="폴더 내 새로운 url 생성")
async def create_folder_url(id, url_in: UrlIn, user: User = Depends(get_current_user)):
    oid = ObjectId()
    url = UrlInDB(
        **url_in.dict(), 
        added_by=User(**user),
        memos_id=oid
        )
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id)}, {"$push": {"urls": jsonable_encoder(url)}},
        return_document=ReturnDocument.AFTER
    )
    db.memo.insert(jsonable_encoder(Memos(_id=oid)))
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_url.put('/folder/{id}/url', summary="폴더 내 특정 url을 찾아, 해당 url의 썸네일, 태그 수정")
async def update_folder_url(id, url_in: UrlIn, user: User = Depends(get_current_user)):
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id), "urls.url": url_in.url}, 
        {"$set": {"urls.$.thumbnail": url_in.thumbnail, "urls.$.tags": url_in.tags}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder_url.delete('/folder/{id}/url', summary="폴더 내 특정 url 삭제")
async def delete_folder_url(id, url_in: UrlIn):
    tmp = db.folder.find_one(
        {"_id": ObjectId(id), "urls.url": url_in.url},
        {"urls.$":1}
    )
    memos_id = tmp["urls"][0]["memos_id"]
    
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id)},
        {"$pull": {"urls": {"url": url_in.url}}}, 
        return_document=ReturnDocument.AFTER
    )
    db.memo.delete_one({"_id": memos_id})
    
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")

