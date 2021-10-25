from typing import List, Optional
from pydantic import BaseModel


class User(BaseModel):
    nickname: str
    permission: Optional[int] = 0


class Highlight(BaseModel):
    start: str
    end: str
    memo: Optional[str] = ""


class Url(BaseModel):
    url: str
    img: Optional[str]
    tags: List[str] = []
    highlights: List[Highlight] = []


class Folder(BaseModel):
    folder_name: str
    users: List[User] = []
    urls: List[Url] = []


class FolderIn(BaseModel):
    folder_name: str
    user: User