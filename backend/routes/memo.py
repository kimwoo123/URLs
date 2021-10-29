from fastapi import Depends, APIRouter, HTTPException, status
from models.folder import User
from models.memo import MemoIn, MemoInDB, Memos, MemoUser
from config.db import db
from serializers.common import serializeDict, serializeList
from fastapi.encoders import jsonable_encoder
# from fastapi.responses import JSONResponse
from bson import ObjectId
from pprint import pprint
from pymongo import ReturnDocument
from .token import get_current_user
from datetime import datetime

memo = APIRouter()


@memo.get('/memo/{memos_id}', summary="url에 달린 모든 메모(memos) 조회", response_model=Memos)
async def find_folder_url_memo(memos_id):
    memos = db.memo.find_one({"_id": memos_id})
    if memos is not None:
        return serializeDict(memos)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"memo {memos_id} not found")


@memo.post('/memo/{memos_id}', summary="url memos 리스트에 memo 생성")
async def create_folder_url_memo(memos_id, memo_in: MemoIn, user: User = Depends(get_current_user)):
    memo_inDB = MemoInDB(
        **memo_in.dict(),
        _id = ObjectId(),
        user = MemoUser(**user),
        created_at = datetime.utcnow()
    )
    memo = db.memo.find_one_and_update(
        {"_id": memos_id}, {"$push": {"memos": jsonable_encoder(memo_inDB)}},
        return_document=ReturnDocument.AFTER
    )
    if memo is not None:
        return serializeDict(memo)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"memo {memos_id} not found")


@memo.put('/memo/{memos_id}/{memo_id}', summary="url memos 리스트에 있는 memo 수정")
async def update_folder_url_memo(memos_id, memo_id, memo_in: MemoIn, user: User = Depends(get_current_user)):
    memo = db.memo.find_one_and_update(
        {"_id": memos_id, "memos._id": memo_id}, 
        {"$set": {"memos.$.highlight": memo_in.highlight, "memos.$.content": memo_in.content}},
        return_document=ReturnDocument.AFTER
    )
    if memo is not None:
        return serializeDict(memo)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"memo {memos_id} not found")

@memo.delete('/memo/{memos_id}/{memo_id}', summary="url memos 리스트에 있는 memo 삭제")
async def delete_folder_url_memo(memos_id, memo_id):
    memo = db.memo.find_one_and_update(
        {"_id": memos_id}, 
        {"$pull": {"memos": {"_id": memo_id}}},
        return_document=ReturnDocument.AFTER
    )
    if memo is not None:
        return serializeDict(memo)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"memo {memos_id} not found")