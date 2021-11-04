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

class UserTag(BaseModel):
    name: str
    count: int


class UserFolder(BaseModel):
    id: PyObjectId = Field(alias='_id')
    name: str
    shared: bool

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }


class UserIn(BaseModel):
    email: EmailStr
    name : str
    picture: HttpUrl

    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "name": "ssafy",
                "picture": "https://via.placeholder.com/200.jpg",
            }
        }


class UserInDB(UserIn):
    tags: List[UserTag] = []
    folders: List[UserFolder] = []
    categories: Dict = CategoryItem
    

class UserOut(UserInDB):
    id: PyObjectId = Field(alias='_id')

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }
