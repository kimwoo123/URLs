from fastapi import APIRouter, HTTPException, status
from models.user import UserIn, UserOut
from typing import List
from config.db import db
from serializers.common import serializeDict, serializeList
from bson import ObjectId
import bcrypt


user = APIRouter()

@user.get('/user', summary="모든 유저 조회", response_model=List[UserOut])
async def find_all_users():
    return serializeList(db.user.find())


@user.get('/user/{id}',  summary="단일 유저 조회", response_model=UserOut)
async def find_one_user(id):
    user = db.user.find_one({"_id": ObjectId(id)})
    if user is not None:
        return serializeDict(user)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {id} not found")


@user.post('/user', summary="새로운 유저 생성", response_model=UserOut, status_code=status.HTTP_201_CREATED)
async def create_user(user: UserIn):
    user.password = bcrypt.hashpw(user.password.encode('utf-8'), bcrypt.gensalt())
    new_user = db.user.insert_one(dict(user))
    user = db.user.find_one({"_id": ObjectId(new_user.inserted_id)})
    if user is not None:
        return serializeDict(user)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {new_user.inserted_id} not found")


@user.put('/user/{id}', response_model=UserOut, summary="유저 정보 수정")
async def update_user(id, user: UserIn):
    user = db.user.find_one_and_update({"_id": ObjectId(id)}, {"$set": dict(user)})
    if user is not None:
        return serializeDict(db.user.find_one({"_id": ObjectId(id)}))
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {id} not found")


@user.delete('/user/{id}', response_model=UserOut, summary="유저 삭제")
async def delete_user(id):
    user = db.user.find_one_and_delete({"_id": ObjectId(id)})
    if user is not None:
        return serializeDict(user)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {id} not found")

