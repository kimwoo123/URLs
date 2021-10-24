from pymongo import MongoClient

mongodb_URI = "mongodb://localhost:27017/"
client = MongoClient(mongodb_URI)

db = client['Urls']


# collection = db['folder']
# collection = db['tag']
# collection = db['user']

