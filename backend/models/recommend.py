from typing import Dict
from pydantic import BaseModel, HttpUrl
from models.user import CategoryItem


class Recommend(BaseModel):
    url: HttpUrl
    categories: Dict = CategoryItem
    count: int = 1


class UrlIn(Recommend):

    class Config:
        schema_extra = {
            "example": {
                "url": "https://www.google.com",
                "categories": {
                    "category1": 0,
                    "category2": 0,
                    "category3": 0,
                    "category4": 0,
                    "category5": 0,
                    "category6": 0,
                    "category7": 0,
                    "category8": 0,
                }
            }
        }