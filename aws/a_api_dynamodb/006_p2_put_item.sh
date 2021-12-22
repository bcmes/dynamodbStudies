#!/bin/bash

# Insert Item

aws dynamodb put-item --cli-input-json file:///home/c3po/Documents/projects/dynamodb/aws/a_api/002_p1_item_definition.json --endpoint-url http://localhost:4566

# Tipos de dados no dynamoDB:
# N = Numeros
# S = String. Os valores de Date são armazenados como strings formatadas em ISO-8601.
# B = Binario
# BOOL = Boleanos, 0 ou 1.

# SS = Conjunto de strings
# NS = Conjunto de numeros
# BS = Conjunto de boleanos

# M = Mapas
# L = Listas

# Forma 2:
aws dynamodb put-item`` \
    --table-name Music \
    --item \
        '{"Artist": {"S": "No One You Know"}, "SongTitle": {"S": "Call Me Today"}, "AlbumTitle": {"S": "Somewhat Famous"}}' \
    --return-consumed-capacity TOTAL \
    --endpoint-url http://localhost:4566
