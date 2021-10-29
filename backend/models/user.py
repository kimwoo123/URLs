from typing import List, Optional
from pydantic import BaseModel, EmailStr, HttpUrl, Field
from bson import ObjectId
from .common import PyObjectId


class UserBase(BaseModel):
    email: EmailStr
    nickname : str
    avatar: HttpUrl
    tags: List[str] = []
    folders: List[str] = []


class UserIn(UserBase):
    password: str

    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "nickname": "ssafy",
                "avatar": "https://via.placeholder.com/200.jpg",
                "tags": [],
                "folders": [],
                "password": "ssafy1234"
            }
        }


class UserOut(UserBase):
    id: PyObjectId = Field(alias='_id')

    class Config:
        schema_extra = {
            "example": {
                "_id": "",
                "email": "ssafy@ssafy.com",
                "nickname": "ssafy",
                "avatar": "https://via.placeholder.com/200.jpg",
                "tags": [],
                "folders": [],
            }
        }
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }
