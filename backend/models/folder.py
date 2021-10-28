from typing import List, Optional
from pydantic import BaseModel, EmailStr, HttpUrl, Field
from bson import ObjectId
from .common import PyObjectId

class User(BaseModel):
    email: EmailStr
    nickname: str
    avatar: HttpUrl
    permission: Optional[int] = 0


class UserIn(BaseModel):
    email: EmailStr
    permission: Optional[int] = 0
    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "permission": 0
            }
        }



class Url(BaseModel):
    url: HttpUrl
    added_by: User
    thumbnail: Optional[HttpUrl]
    tags: List[str] = []
    memo_id: PyObjectId

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }


class UrlIn(BaseModel):
    url: HttpUrl
    thumbnail: Optional[HttpUrl]
    tags: List[str] = []
    class Config:
        schema_extra = {
            "example": {
                "url": "https://www.naver.com/",
                "thumbnail": "https://via.placeholder.com/200.jpg",
                "tags": [],
            }
        }


class UrlInDB(Url):
    pass



class FolderIn(BaseModel):
    folder_name: str
    class Config:
        schema_extra = {
            "example": {
                "folder_name": "folder1",
            }
        }


class FolderInDB(BaseModel):
    folder_name: str
    shared: bool = False
    users: List[User] = []
    urls: List[Url] = []


class FolderOut(FolderInDB):
    id: PyObjectId = Field(alias='_id')

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }