from typing import List, Optional
from pydantic import BaseModel


class Tag(BaseModel):
    tag_name: str
    urls: List[str] = []