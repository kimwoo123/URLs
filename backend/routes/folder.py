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
folder_url = APIRouter()
folder_user = APIRouter()

# 내 모든 폴더 조회 만들기


@folder.get('/folder/{id}', summary="폴더 상세 조회", response_model=FolderOut)
async def find_one_folder(id):
    folder = db.folder.find_one({"_id": ObjectId(id)})
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


# 추후 체크
@folder.post('/folder', summary="폴더 생성", response_model=FolderOut)
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



# 폴더 유저 관리

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
async def update_folder_user(id, url_in: UrlIn, user: User = Depends(get_current_user)):
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(id), "urls.url": url_in.url}, 
        {"$set": {"urls.$.thumbnail": url_in.thumbnail, "urls.$.tags": url_in.tags}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")



@folder_url.delete('/folder/{id}/url', summary="폴더 url 삭제")
async def delete_folder_user(id, url_in: UrlIn):
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


# @folder_url.post('/folder/{id}/url', summary="폴더 태그 추가 및 삭제")
# async def update_folder_user(id, tag):
#     pass


# @folder_url.put('/folder/{id}/url/', summary="폴더 하이라이트 생성")
# async def update_folder_user(id, highlight):
#     pass

# @folder_url.put('/folder/{id}/url', summary="폴더 하이라이트 수정")
# async def update_folder_user(id, highlight):
#     pass