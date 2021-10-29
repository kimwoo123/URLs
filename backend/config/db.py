from pymongo import MongoClient

# 로컬용
# mongodb_URI = "mongodb://localhost:27017/"
# client = MongoClient(mongodb_URI)

# 서버용
username = "eagle"
password = "k5b201eagle"
ip_address = "13.124.101.173"
port = "27017"
mongodb_URI = f"mongodb://{username}:{password}@{ip_address}:{port}"
client = MongoClient(mongodb_URI)


db = client['Urls']
