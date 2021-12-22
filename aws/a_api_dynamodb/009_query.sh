#!/bin/bash

# Lista todos os itens de uma chave primaria informada.

# Consulta em Tabela
aws dynamodb query \
    --table-name Music \
    --key-condition-expression "Artist = :name" \
    --expression-attribute-values  '{":name":{"S":"Acme Band"}}' \
    --endpoint-url http://localhost:4566

# Consulta em GSI
aws dynamodb query \
    --table-name Music \
    --index-name AlbumTitle-index \
    --key-condition-expression "AlbumTitle = :name" \
    --expression-attribute-values  '{":name":{"S":"Somewhat Famous"}}' \
    --endpoint-url http://localhost:4566

# Exemplo com debug habilitado e outras opcoes.
aws dynamodb query \
    --table-name Music \
    --projection-expression "AlbumTitle" \
    --key-condition-expression "#A = :name" \
    --expression-attribute-names '{"#A":"Artist"}' \
    --expression-attribute-values  '{":name":{"S":"Acme Band"}}' \
    --page-size 5 \
    --debug \
    --endpoint-url http://localhost:4566
