from fastapi import FastAPI
from routes.user import user
from routes.token import token

# uvicorn main:app --reload
# source venv/Scripts/activate
# pip freeze > requirements.txt

app = FastAPI()
app.include_router(token)
app.include_router(user)


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
