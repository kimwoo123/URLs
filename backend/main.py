from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from routes.user import user
from routes.token import token
from routes.tag import tag
from routes.folder import folder, folder_url, folder_user


app = FastAPI()

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
