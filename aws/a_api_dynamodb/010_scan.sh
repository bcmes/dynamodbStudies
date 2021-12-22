#!/bin/bash

# Lista todos os itens da tabela
aws dynamodb scan --table-name Music --endpoint-url http://localhost:4566
