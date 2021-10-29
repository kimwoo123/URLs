from typing import List, Optional
from pydantic import BaseModel, HttpUrl, Field
from bson import ObjectId
from .common import PyObjectId

class TagIn(BaseModel):
    tag_name: str

    class Config:
        schema_extra = {
            "example": {
                "tag_name": "site",
            }
        }


class TagInDB(BaseModel):
    tag_name: str
    total: bool = False
    urls: List[HttpUrl] = []
    count: int = len(urls)


class TagOut(TagInDB):
    id: PyObjectId = Field(alias='_id')

    class Config:
        schema_extra = {
            "example": {
                "_id": "",
                "tag_name": "ssafy@ssafy.com",
                "total": False,
                "count": 2,
                "urls": [
                    "https://edu.ssafy.com/", 
                    "https://www.naver.com/"
                ]
            }
        }
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }