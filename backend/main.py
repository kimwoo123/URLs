from fastapi import FastAPI
from routes.user import user
from routes.token import token
from routes.tag import tag
from routes.folder import folder, folder_url, folder_user
# uvicorn main:app --reload
# source venv/Scripts/activate
# pip freeze > requirements.txt

app = FastAPI()
app.include_router(token, tags=["token"])
app.include_router(user, tags=["user"])
app.include_router(tag, tags=["tag"])
app.include_router(folder, tags=["folder"])
app.include_router(folder_user, tags=["folder/user"])
app.include_router(folder_url, tags=["folder/url"])

# 400 Bad Request
# 401 Unauthorized
# 403 Forbidden
# 404 Not Found
# 405 Method not allowed
# 500 Internal Error
# 502 Bad Gateway 
# 504 Timeout
# 200 OK
# 201 Created
