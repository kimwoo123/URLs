from fastapi import APIRouter, HTTPException, status
from models.tag import TagIn, TagInDB, TagOut
from typing import List
from config.db import db
from serializers.common import serializeDict, serializeList
from bson import ObjectId
from pymongo import ReturnDocument

tag = APIRouter()


@tag.get('/tag',  summary="모든 태그 조회", response_model=List[TagOut])
async def find_all_tags():
    return serializeList(db.tag.find())


@tag.get('/tag/{id}', summary="단일 태그 조회", response_model=TagOut)
async def find_one_tag(id):
    tag = db.tag.find_one({"_id": ObjectId(id)})
    if tag is not None:
        return serializeDict(tag)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"tag {id} not found")


@tag.post('/tag', summary="새로운 태그 생성", response_model=List[TagOut])
async def create_tag(tag_in: TagIn):
    new_tag = TagInDB(**tag_in.dict())
    db.tag.insert_one(dict(new_tag))
    return serializeList(db.tag.find())


@tag.put('/tag/{id}', summary="태그 정보 수정", response_model=TagOut)
async def update_tag(id, tag_in: TagIn):
    tag = db.tag.find_one_and_update(
        {"_id": ObjectId(id)}, {"$set": dict(tag_in)},
        return_document=ReturnDocument.AFTER
    )
    if tag is not None:
        return serializeDict(tag)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"tag {id} not found")

# # 체크
@tag.post('/tag/{id}', summary="태그에 새로운 url 추가 및 삭제", response_model=TagOut)
async def update_tag_url(id, url):
    tag = db.tag.find_one({"_id": ObjectId(id)})
    if url in tag["urls"]:
        tag["urls"].remove(url)
    else:
        tag["urls"].append(url)
    tag = db.tag.find_one_and_update(
        {"_id": ObjectId(id)}, {"$set": dict(tag)},
        return_document=ReturnDocument.AFTER
    )
    return serializeDict(tag)


@tag.delete('/tag/{id}', summary="태그 삭제",  response_model=TagOut)
async def delete_tag(id):
    tag = db.tag.find_one_and_delete({"_id": ObjectId(id)})
    if tag is not None:
        return serializeDict(tag)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"tag {id} not found")

