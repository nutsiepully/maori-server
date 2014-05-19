
curl -F "file=@src/main/resources/barometer-model.model" 'localhost:9979/maori-server/model?name=barometer&version=v2'

curl -X POST 'localhost:9979/maori-server/model/device/associate?name=barometer&version=v2&deviceId=d1'

curl -X GET 'localhost:9979/maori-server/model?name=barometer&version=v1'
