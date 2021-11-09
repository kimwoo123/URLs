from fastapi import APIRouter, HTTPException, status
from models.recommend import Recommend, UrlIn
from config.db import db
from serializers.common import serializeDict, serializeList
from bson import ObjectId
import numpy as np


recommend = APIRouter()


@recommend.get('/recommend', summary="전체 url DB 속 url 있는지 조회")
async def find_one_url(url):
    url = db.recommend.find_one({"url": url})
    if url is not None:
        return serializeDict(url)
    return HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"url {url} not found")


@recommend.get('/recommend/{id}', summary="urls 추천")
async def recommend_urls(id, count: int):
    urls_with_weight = list(db.recommend.find({}, {"_id": 0, "url": 1, "categories": 1}))
    recommended_urls = []
    
    # If url exists, Recommend!
    if urls_with_weight is not None:
        urls = np.array([[url_with_weight["url"]] for url_with_weight in urls_with_weight])
        weights = np.array([[url_with_weight["categories"][category] for category in url_with_weight["categories"]] for url_with_weight in urls_with_weight])

        user = db.user.find_one({"_id": ObjectId(id)}, {"_id": 0, "categories": 1})
        user = np.array([user["categories"][x] for x in user["categories"]])
        user = user / np.sum(user)

        weights = np.sum(np.square(weights - user), axis=1, keepdims=True)
        
        recommended_urls = np.concatenate([urls, weights], axis=1).tolist()
        recommended_urls.sort(key=lambda x: float(x[1]))
        recommended_urls = [url_with_score[0] for url_with_score in recommended_urls][:count]

    return recommended_urls


@recommend.post('/recommend', summary="추천을 위한 전체 url DB에 신규 url 추가", status_code=status.HTTP_201_CREATED)
async def create_url(url: UrlIn):
    # Find target category
    prefer_category = ''
    for item in url.categories:
        if url.categories[item] == 1:
            prefer_category = item
            break

    # Create or Update categories of the url
    url_in_db = db.recommend.find_one({"url": url.url})
    # Make sure that the url is not in recommend DB
    if url_in_db is not None:
        for item in url_in_db["categories"]:
            if item == prefer_category:
                url_in_db["categories"][item] = (url_in_db["categories"][item] * url_in_db["count"] + 1) / (url_in_db["count"] + 1)
            else:
                url_in_db["categories"][item] = url_in_db["categories"][item] * url_in_db["count"] / (url_in_db["count"] + 1)
        db.recommend.find_one_and_update({"url": url.url}, {"$set": dict(url_in_db)})
    else:
        db.recommend.insert_one(dict(url))

    return serializeDict(url_in_db)


@recommend.put('/recommend', summary="추천을 위한 전체 url DB 속 단일 URL 수정")
async def update_url(url: UrlIn):
    # Find target category
    prefer_category = ''
    for item in url.categories:
        if url.categories[item] == 1:
            prefer_category = item
            break
    
    # Update categories of the url
    url_in_db = db.recommend.find_one({"url": url.url})
    for item in url_in_db["categories"]:
        if item == prefer_category:
            url_in_db["categories"][item] = (url_in_db["categories"][item] * url_in_db["count"] + 1) / (url_in_db["count"] + 1)
        else:
            url_in_db["categories"][item] = url_in_db["categories"][item] * url_in_db["count"] / (url_in_db["count"] + 1)
    db.recommend.find_one_and_update({"url": url.url}, {"$set": dict(url_in_db)})

    return serializeDict(url_in_db)