from elasticsearch import Elasticsearch
from fastapi import APIRouter

search = APIRouter()
es = Elasticsearch(hosts="elastic:k5b201eagle@k5b201.p.ssafy.io", port=9200)

@search.get('/search', summary="검색")
async def search_tag(searchText: str):
  res = es.search(
    index="users_v5",
    body={
      "query": {
        "match": {
          "urls.tags": {
            "query": searchText,
            "fuzziness": "AUTO"
          }
        }
      }
    }
  )
  for hit in res['hits']['hits']:
    print(hit['_source']['email'])
  return res
