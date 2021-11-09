from elasticsearch import Elasticsearch
from fastapi import APIRouter

search = APIRouter()
es = Elasticsearch(hosts="elastic:k5b201eagle@k5b201.p.ssafy.io", port=9200)

@search.post('/search', summary="검색")
async def search_tag(searchText: str):
  res = es.search(
    index="users_v5",
    body={
      "query": {
        "match": {
          "email": {
            "query": searchText,
            "fuzziness": "auto"
          }
        }
      }
    },
  )
  for hit in res['hits']['hits']:
    print(hit['_source']['email'])
  return res
