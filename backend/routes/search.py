from elasticsearch import Elasticsearch
from fastapi import APIRouter

search = APIRouter()
es = Elasticsearch(hosts="k5b201.p.ssafy.io", port=9200)

@search.get('/search', summary="검색")
async def search_tag():
  res = es.search(
    index="users_v3",
    body={
      "query": {
        "fuzzy": {
          "email": "rmlf"
        }
      }
    },
  )
  for hit in res['hits']['hits']:
    print(hit['_source']['email'])
  return res
  
  
