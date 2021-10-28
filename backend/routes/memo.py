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

memo = APIRouter()


@memo.get('/folder/{folder_id}/url/memo/{memo_id}', summary="폴더 url 메모 조회 | 구현X")
async def find_folder_url_memo(folder_id, memo_id):
    pass


@memo.post('/folder/{folder_id}/url/memo', summary="폴더 url 메모 생성 | 구현X")
async def create_folder_url_memo(folder_id):
    pass


@memo.put('/folder/{folder_id}/url/memo/{memo_id}', summary="폴더 url 메모 수정 | 구현X")
async def update_folder_url_memo(folder_id, memo_id):
    pass


@memo.delete('/folder/{folder_id}/url/memo/{memo_id}', summary="폴더 url 메모 삭제 | 구현X")
async def delete_folder_url_memo(folder_id, memo_id):
    pass