from fastapi import APIRouter, HTTPException, status
from models.tag import Tag
from config.db import db
from schemas.user import serializeDict, serializeList
from bson import ObjectId


tag = APIRouter()


@tag.get('/tag',  summary="모든 태그 조회")
async def find_all_tags():
    return serializeList(db.tag.find())


@tag.get('/tag/{tag_name}', summary="단일 태그 조회")
async def find_one_tag(tag_name):
    tag = db.tag.find_one({"tag_name": tag_name})
    if tag is not None:
        return serializeDict(tag)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"tag {tag_name} not found")


@tag.post('/tag', summary="새로운 단일 태그 생성")
async def create_tag(tag: Tag):
    db.tag.insert_one(dict(tag))
    return serializeList(db.tag.find())


@tag.put('/tag/{tag_name}', summary="단일 태그 수정")
async def update_tag(tag_name, tag: Tag):
    tag = db.tag.find_one_and_update({"tag_name": tag_name}, {"$set": dict(tag)})
    if tag is not None:
        return serializeDict(db.tag.find_one({"_id": ObjectId(tag['_id'])}))
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"tag {tag_name} not found")


@tag.post('/tag/{tag_name}', summary="단일 태그에 새로운 url 추가 및 삭제")
async def update_tag(tag_name, url):
    tag = db.tag.find_one({"tag_name": tag_name})
    if url in tag["urls"]:
        tag["urls"].remove(url)
    else:
        tag["urls"].append(url)
    db.tag.find_one_and_update({"tag_name": tag_name}, {"$set": dict(tag)})
    return serializeDict(db.tag.find_one({"tag_name": tag_name}))


@tag.delete('/tag/{tag_name}', summary="단일 태그 삭제")
async def delete_tag(tag_name):
    tag = db.tag.find_one_and_delete({"tag_name": tag_name})
    if tag is not None:
        return serializeDict(tag)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"tag {tag_name} not found")

