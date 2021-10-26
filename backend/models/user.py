from typing import List, Optional
from pydantic import BaseModel, EmailStr, HttpUrl


class UserBase(BaseModel):
    email: EmailStr
    nickname : str
    avatar: HttpUrl
    folders: List[str] = []


class UserIn(BaseModel):
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
    pass

    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "nickname": "ssafy",
                "avatar": "https://via.placeholder.com/200.jpg",
                "folders": [],
            }
        }


class Token(BaseModel):
    access_token: str
    token_type: str
