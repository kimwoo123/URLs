from typing import List, Optional
from pydantic import BaseModel, EmailStr


class UserBase(BaseModel):
    email: EmailStr
    nickname : str
    avatar: str
    folders: List[str] = []


class UserIn(UserBase):
    password: str


class UserOut(UserBase):
    pass


class Token(BaseModel):
    access_token: str
    token_type: str
