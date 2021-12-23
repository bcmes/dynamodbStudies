#!/bin/bash

aws dynamodb scan --table-name Movies --endpoint-url http://localhost:4566
