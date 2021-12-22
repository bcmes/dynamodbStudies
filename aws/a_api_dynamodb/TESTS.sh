#!/bin/bash

echo `date -d '+0 minutes' +%s`

aws dynamodb scan --table-name Music --endpoint-url http://localhost:4566
