from typing import List, Optional
from pydantic import BaseModel, EmailStr, HttpUrl


class User(BaseModel):
    email: EmailStr
    nickname: str
    avatar: HttpUrl
    permission: Optional[int] = 0


class Highlight(BaseModel):
    start: str
    end: str
    memo: Optional[str] = ""


class Url(BaseModel):
    url: HttpUrl
    img: Optional[HttpUrl]
    tags: List[str] = []
    highlights: List[Highlight] = []


class Folder(BaseModel):
    folder_name: str
    users: List[User] = []
    urls: List[HttpUrl] = []


class FolderIn(BaseModel):
    folder_name: str
    user: User