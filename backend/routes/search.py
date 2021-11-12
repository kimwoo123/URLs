from elasticsearch import Elasticsearch
from fastapi import APIRouter

search = APIRouter()
es = Elasticsearch(hosts="elastic:k5b201eagle@k5b201.p.ssafy.io", port=9200)

@search.get('/search', summary="검색")
async def search_tag(searchText: str, folder: str):
  print(searchText, folder)
  res = es.search(
    index="folder_v2",
    body=
    {
      "query": {
        "bool": {
          "must": [
            {
              "match": {
                "_id": folder
              }
            }
          ],
          "filter": [
            {
              "match": {
                "urls.tags": {
                  "query": searchText,
                  "fuzziness": "AUTO"
                }
              }
            }
          ]
        }
      }
    }
  )
  print(res)
  for hit in res['hits']['hits']:
    print(hit['_source'])
  return res['hits']['hits']
