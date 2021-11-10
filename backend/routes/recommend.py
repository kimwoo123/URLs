from fastapi import APIRouter, HTTPException, status
from serializers.common import serializeDict
from bson import ObjectId
from config.db import db
from models.recommend import UrlIn

import numpy as np
import requests
from bs4 import BeautifulSoup
import re
# 로컬 테스트 시 아래는 주석처리 - 1 (아래에 2도 있음)
# -----------------------------
from konlpy.tag import Mecab
mecab = Mecab()
# -----------------------------


recommend = APIRouter()

@recommend.get('/tags', summary="해당 url 의 tags 추천")
async def find_tags(url, count: int):
    if url is not None:
        try:
            headers = {
                "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
            }
            res = requests.get(url, headers=headers)
            print('\nrequest succeed\n')
            res = res.content

            soup = BeautifulSoup(res, 'html.parser')
            # Basic
            soup_1 = [h.string.strip() if h.string else '' for h in soup.find_all('h1')]
            soup_2 = [h.string.strip() if h.string else '' for h in soup.find_all('h3')]
            soup_3 = [p.string.strip() if p.string else '' for p in soup.find_all('p')]
            print('\nBeautifulsoup parsing succeed\n')
            # Some domain need specific headers
            try:
                # Naver
                naver = [soup.find('div', id="articleBodyContents").text]
            except:
                naver = []

            soup = soup_1 + soup_2 + soup_3 + naver
            # Concatenate to a string
            soup = ' '.join(soup).strip()
            print('\nConcatenate succeed\n')
            # Remove noise str
            removal_list = "‘, ’, ◇, ‘, ”,  ’, ', ·, \“, ·, △, ●,  , ■, (, ), \", >>, `, /, -,∼,=,ㆍ<,>, .,?, !,【,】, …, ◆,%"
            soup = re.sub("[.,\'\"’‘”“!?]", "", soup)
            soup = re.sub("[^가-힣0-9a-zA-Z\\s]", " ", soup)
            soup = re.sub("\s+", " ", soup)
            soup = soup.translate(str.maketrans(removal_list, ' '*len(removal_list)))
            soup = soup.strip()
            print('\n', soup, '\n')

            # 로컬테스트 시 아래는 주석처리 - 2
            # ---------------------------------------------
            soup = mecab.nouns(soup)
            print('\nMecab nouns function succeed\n')
            print('\n', soup, '\n')
            result = dict()
            for item in soup:
                if result.get(item):
                    result[item] += 1
                else:
                    result[item] = 1
            
            result = [(k, v) for k, v in result.items()]
            result.sort(reverse=True, key=lambda x: x[1])
            result = [k for k, _ in result]
            print('\nMake tags succeed\n')
            print('\n', result, '\n')
            return result[:int(count)]
            # ---------------------------------------------
        except:
            return HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"Error occured\nurl {url} prevent request module")

    return HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"url {url} not found")


@recommend.get('/recommend', summary="전체 url DB 속 url 있는지 조회")
async def find_one_url(url):
    url = db.recommend.find_one({"url": url})
    if url is not None:
        return serializeDict(url)
    return HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"url {url} not found")


@recommend.get('/recommend/{id}', summary="urls 추천")
async def recommend_urls(id, count: int):
    urls_with_weight = list(db.recommend.find({}, {"_id": 0, "url": 1, "categories": 1, "count": 1}))
    recommended_urls = []
    
    # If url exists, Recommend!
    if urls_with_weight is not None:
        # Only urls (count > 2) (for preventing noise)
        urls = np.array(
            [[url_with_weight["url"]] for url_with_weight in urls_with_weight
            if url_with_weight["count"] > 2]
        )

        # Only weights (count > 2) (for preventing noise)
        weights = np.array(
            [[url_with_weight["categories"][category] for category in url_with_weight["categories"]]
            for url_with_weight in urls_with_weight
            if url_with_weight["count"] > 2]
        )

        # Make recommendation when count > 2
        if urls is not None and weights is not None:
            user = db.user.find_one({"_id": ObjectId(id)}, {"_id": 0, "categories": 1})
            user = np.array([user["categories"][x] for x in user["categories"]])
            # Normalize user categories
            user = user / np.sum(user)

            # Calculate distance
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
        url_in_db["count"] = url_in_db["count"] + 1
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
    url_in_db["count"] += 1
    db.recommend.find_one_and_update({"url": url.url}, {"$set": dict(url_in_db)})

    return serializeDict(url_in_db)