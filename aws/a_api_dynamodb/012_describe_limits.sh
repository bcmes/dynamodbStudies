#!/bin/bash

# Descrição das cotas de taxa de transferência provisionada:
#  retorna as cotas de capacidade de leitura e gravação atuais da conta e da região atuais da AWS.
aws dynamodb describe-limits --endpoint-url http://localhost:4566