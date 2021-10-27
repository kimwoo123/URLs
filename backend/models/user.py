from typing import List, Optional
from pydantic import BaseModel, EmailStr, HttpUrl, Field
from bson import ObjectId

class PyObjectId(ObjectId):

    @classmethod
    def __get_validators__(cls):
        yield cls.validate

    @classmethod
    def validate(cls, v):
        if not ObjectId.is_valid(v):
            raise ValueError('Invalid objectid')
        return ObjectId(v)

    @classmethod
    def __modify_schema__(cls, field_schema):
        field_schema.update(type='string')


class UserBase(BaseModel):
    email: EmailStr
    nickname : str
    avatar: HttpUrl
    folders: List[str] = []


class UserIn(UserBase):
    password: str

    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "nickname": "ssafy",
                "avatar": "https://via.placeholder.com/200.jpg",
                "folders": [],
                "password": "ssafy1234"
            }
        }


class UserOut(UserBase):
    id: PyObjectId = Field(alias='_id')

    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "nickname": "ssafy",
                "avatar": "https://via.placeholder.com/200.jpg",
                "folders": [],
                "_id": "",
            }
        }
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }


class Token(BaseModel):
    access_token: str
    token_type: str
