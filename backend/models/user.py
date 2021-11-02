from typing import List, Optional, Dict
from pydantic import BaseModel, EmailStr, HttpUrl, Field
from bson import ObjectId
from .common import PyObjectId


CategoryItem = {
    "category1": 0,
    "category2": 0,
    "category3": 0,
    "category4": 0,
    "category5": 0,
    "category6": 0,
    "category7": 0,
    "category8": 0,
}


class UserBase(BaseModel):
    email: EmailStr
    nickname : str
    avatar: HttpUrl
    tags: List[str] = []
    folders: List[str] = []
    categories: Dict = CategoryItem


class UserIn(UserBase):
    password: str

    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "nickname": "ssafy",
                "avatar": "https://via.placeholder.com/200.jpg",
                "password": "ssafy1234"
            }
        }


class UserOut(UserBase):
    id: PyObjectId = Field(alias='_id')

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }
