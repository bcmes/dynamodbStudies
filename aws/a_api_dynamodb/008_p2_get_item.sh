#!/bin/bash
# get-item retorna somente um item sempre, o da chave informada.
aws dynamodb get-item --table-name Music --key '{"Artist": {"S": "No One You Know"}, "SongTitle": {"S": "Call Me Today"}}' --endpoint-url http://localhost:4566

# Forma 2
aws dynamodb get-item --cli-input-json file:///home/c3po/Documents/projects/dynamodb/aws/a_api_dynamodb/004_p1_key_definitions_item.json --endpoint-url http://localhost:4566