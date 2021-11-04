
# def userEntity(item) -> dict:
#     return {
#         "id": str(item["_id"]),
#         "email": item["email"],
#         "name": item["name"],
#         "password": item["password"],
#         "picture": item["picture"],
#         "folders": item["folders"],
#     }

# def usersEntity(entity) -> list:
#     return [userEntity(item) for item in entity]

def serializeDict(item) -> dict:
    return {**{i:str(item[i]) for i in item if i=='_id'}, **{i:item[i] for i in item if i!='_id'}}

def serializeList(entity) -> list:
    return [serializeDict(a) for a in entity]