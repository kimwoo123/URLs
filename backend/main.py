from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from routes.user import user
from routes.token import token
from routes.tag import tag
from routes.folder import folder, folder_url, folder_user


tags_metadata = [
    {
        "name": "token",
        "description": "토큰 발급. **로그인** 포함",
    },
    {
        "name": "user",
        "description": "유저 CRUD. **회원가입** 포함",
    },
    {
        "name": "tag",
        "description": "태그 CRUD",
    },
    {
        "name": "folder",
        "description": "폴더 CRUD (**폴더 자체)**",
    },
    {
        "name": "folder/user",
        "description": "폴더 user CUD (폴더 내의 **user 필드**)",
    },
    {
        "name": "folder/url",
        "description": "구현X | 폴더 url 관련",
    },
]


app = FastAPI(
    title="Project Urls",
    description="팀 이글아이 SSAFY 자율 프로젝트 Urls",
    version="0.0.1",
    openapi_tags=tags_metadata
)

origins = [
    "http://localhost",
    "https://localhost:8000",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(token, tags=["token"])
app.include_router(user, tags=["user"])
app.include_router(tag, tags=["tag"])
app.include_router(folder, tags=["folder"])
app.include_router(folder_user, tags=["folder/user"])
app.include_router(folder_url, tags=["folder/url"])


# uvicorn main:app --reload
# source venv/Scripts/activate
# pip freeze > requirements.txt
