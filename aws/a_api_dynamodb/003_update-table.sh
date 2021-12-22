#!/bin/bash

# Criando um GSI

aws dynamodb update-table \
    --table-name Music \
    --attribute-definitions \
            "AttributeName=AlbumTitle, AttributeType=S" \
    --global-secondary-index-updates \
        "[{\"Create\":{ \
                \"IndexName\": \"AlbumTitle-index\", \
                \"KeySchema\":[{\"AttributeName\":\"AlbumTitle\",\"KeyType\":\"HASH\"}], \
                \"ProvisionedThroughput\": \
                      {\"ReadCapacityUnits\": 10, \"WriteCapacityUnits\": 5}, \
                      \"Projection\":{\"ProjectionType\":\"ALL\"}}}]" \
    --endpoint-url http://localhost:4566