#!/bin/bash

#UpdateItem se comporta com uma operação "upsert":
# O item será atualizado, ou inserido se nao existir na tabela.
aws dynamodb update-item \
    --table-name Music \
    --key '{ "Artist": {"S": "Acme Band"}, "SongTitle": {"S": "Happy Day"}}' \
    --update-expression "SET AlbumTitle = :newval" \
    --expression-attribute-values '{":newval":{"S":"Updated Album Title"}}' \
    --return-values ALL_NEW \
    --endpoint-url http://localhost:4566