#!/bin/bash
# Criar a tabela Music definida no json 000_p1_definition_table.json
aws dynamodb create-table --cli-input-json file:///home/c3po/Documents/projects/dynamodb/aws/a_api_dynamodb/000_p1_definition_table.json --endpoint-url http://localhost:4566

# Tooltip:
# CreateTable: para criar uma tabela de modo provisionado.
# ProvisionedThroughput, definido em "000_p1_definition_table.json":
# E o número de leituras e gravações por segundo para esta tabela.

# Forma 2: Criar tabela no modo provisionado
aws dynamodb create-table \
    --table-name Music02 \
    --attribute-definitions \
        "AttributeName=Artist, AttributeType=S" \
        "AttributeName=SongTitle, AttributeType=S" \
    --key-schema \
        "AttributeName=Artist, KeyType=HASH" \
        "AttributeName=SongTitle, KeyType=RANGE" \
    --provisioned-throughput \
        "ReadCapacityUnits=1, WriteCapacityUnits=1" \
    --endpoint-url http://localhost:4566

# Forma 3: Criar tabela no modo sob demanda
aws dynamodb create-table \
    --table-name Music \
    --attribute-definitions \
        AttributeName=Artist,AttributeType=S \
        AttributeName=SongTitle,AttributeType=S \
    --key-schema \
        AttributeName=Artist,KeyType=HASH \
        AttributeName=SongTitle,KeyType=RANGE \
    --billing-mode=PAY_PER_REQUEST