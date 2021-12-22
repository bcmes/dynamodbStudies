#!/bin/bash
# PartiQL so esta disponivel na versao 1 do AWS CLI e em algumas regioes.
# Antes execute "../a_api/000_p2_create_table.sh"
aws dynamodb execute-statement --statement \
"insert into Music value {'Artist': 'No One You Know', 'SongTitle': 'Call Me Today', 'AlbumTitle': 'someonewhat famous', 'Year' : true, 'Genre' : 'Acme'}"

# Exemplo de Select:
"select AlbumTitle, Year, Price
 FROM Music
 WHERE Artist='No One You Know' AND SongTitle = 'Call Me Today'"

# fonte: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/ql-reference.select.html