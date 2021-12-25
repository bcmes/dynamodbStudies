#!/bin/bash

# Importa os itens no formato Json(formato dynamo) para uma tabela.

aws dynamodb batch-write-item --request-items file:///home/c3po/Documents/projects/dynamodb/aws/data/ProductCatalog.json --endpoint-url http://localhost:4566

# Isso significa que posso exportar os itens de uma tabela e importar em outro local.
