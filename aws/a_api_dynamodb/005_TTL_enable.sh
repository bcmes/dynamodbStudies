#!/bin/bash

# Basta informar um atributo "Data" de tipo Number, que recebera valores no formato ePoch.
# Quando a data/hora deste atributo for alcancado, o item e deletado
# ,Essa e a unica logica possivel de delecao TTL.

# Regra oficial: Se o valor da marca de hora for anterior à hora atual
# , mas não cinco anos ou mais (a fim de evitar a possibilidade de exclusão acidental
# causada por um valor de TTL mal-formado), o item será definido como expirado.

# Pode-se habiliar Streams(24hrs), para realizar operacoes com o registro deletado.

# O TTL geralmente exclui os itens expirados em até 48 horas após a expiração.

# Os itens que passaram da expiração, mas ainda não foram excluídos poderão ser atualizados,
# e atualizações bem-sucedidas para alterar ou remover o atributo de expiração serão respeitadas.

# Regras:
# A. O item deverá conter o atributo especificado quando o TTL foi habilitado na tabela.
# B. O valor do atributo TTL deve ser um tipo de dados Number.
# C. O valor do atributo TTL deve ser um time stamp no formato de tempo do Unix epoch em segundos.
# D. O valor do atributo TTL deve ser um time stamp com uma expiração NAO superior a cinco anos no passado.

# Observacoes:
# fonte: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/time-to-live-ttl-before-you-start.html


# 1. Habilite o TTL na tabela.

aws dynamodb update-time-to-live --table-name Music \
--time-to-live-specification "Enabled=true, AttributeName=ttl" \
--endpoint-url http://localhost:4566

# 2. Descreva o TTL na tabela.

aws dynamodb describe-time-to-live --table-name Music \
--endpoint-url http://localhost:4566

# 3. Adicionando item à tabela com atributo de vida útil com 1min.
# Para obter data/hora em epoch no linux = date +%s
EXP=`date -d '+1 minutes' +%s`

aws dynamodb put-item --table-name "Music" \
--item '{"Artist": {"S": "Acme Band"}, "SongTitle": {"S": "Happy Day"}, "ttl": {"N": "'$EXP'"}}' \
--endpoint-url http://localhost:4566

# Localmente o TTL nao funcionou, nao sei se fiz algo errado ou se nao funciona localmente !!!