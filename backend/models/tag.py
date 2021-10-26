from typing import List, Optional
from pydantic import BaseModel, HttpUrl

class Tag(BaseModel):
    tag_name: str
    urls: List[HttpUrl] = []