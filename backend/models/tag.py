from typing import List, Optional
from pydantic import BaseModel, HttpUrl


class Tag(BaseModel):
    tag_name: str
    urls: List[HttpUrl] = []

    class Config:
        schema_extra = {
            "example": {
                "tag_name": "site",
                "urls": [
                    "https://edu.ssafy.com/",
                    "https://www.naver.com/"
                ],
            }
        }