#!/bin/bash

# Descreve a tabela
aws dynamodb describe-table --table-name Music --endpoint-url http://localhost:4566

# Retornando somente campos especificos
aws dynamodb describe-table \
    --table-name TestTable \
    --query "Table.[TableName,TableStatus,ProvisionedThroughput]"
