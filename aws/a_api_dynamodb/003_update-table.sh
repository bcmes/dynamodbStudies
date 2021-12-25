#!/bin/bash

# Exemplo: Criando um GSI

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

# Definir uma GSI ou LSI e uma modificacao na tabela.
# A operação UpdateTable permite que você execute uma das seguintes ações:
#  Modificar as configurações de taxa de rendimento provisionada de uma tabela (para tabelas de modo provisionadas).
#  Alterar o modo de capacidade de leitura/gravação de tabela.
#  Manipular índices secundários globais na tabela.
#  Habilite ou desabilite o DynamoDB Streams na tabela.

# Exemplo: alterando as configurações de taxa de transferência provisionada
aws dynamodb update-table \
    --table-name Music \
    --provisioned-throughput ReadCapacityUnits=20,WriteCapacityUnits=10

# Exemplo: alterando do modo provisionado para onDemand
aws dynamodb update-table \
    --table-name Music \
    --billing-mode PAY_PER_REQUEST

# Você pode alternar entre os modos de capacidade de leitura/gravação uma vez a cada 24 horas.

# Porque que todas as tabelas nao sao OnDemand: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/ProvisionedThroughput.html
