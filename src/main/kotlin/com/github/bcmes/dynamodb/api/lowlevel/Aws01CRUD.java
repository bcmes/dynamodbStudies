package com.github.bcmes.dynamodb.api.lowlevel;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Aws01CRUD {

    private AmazonDynamoDB amazonDynamoDBClient;

    Aws01CRUD(AmazonDynamoDB amazonDynamoDBClient){
        this.amazonDynamoDBClient = amazonDynamoDBClient;
    }

    public void getItem(){
        HashMap<String, AttributeValue> key = new HashMap<>();
        key.put("Artist", new AttributeValue().withS("No One You Know"));
        key.put("SongTitle", new AttributeValue().withS("Call Me Today"));

        GetItemRequest request = new GetItemRequest()
                .withTableName("Music")
                .withKey(key);

        try {
            GetItemResult result = this.amazonDynamoDBClient.getItem(request);
            if (result.getItem() != null) {
                AttributeValue albumTitle = result.getItem().get("AlbumTitle");
                System.out.println("The song was released in " + albumTitle.getS());
            } else {
                System.out.println("No matching song was found");
            }
        } catch (Exception e) {
            System.err.println("Unable to retrieve data: ");
            System.err.println(e.getMessage());
        }
    }
}
/**
 * O DynamoDB Streams tem sua própria API de baixo nível, que é separada.
 * O DynamoDB usa JSON somente como um protocolo de transporte,
 *  não como um formato de armazenamento. O DynamoDB não armazena dados
 *  persistentemente no formato JSON.
 *
 */

/** PROTOCOLO DE TRANSFERENCIA
 * JSON em uma estrutura que carrega os dados e seus tipos, exceto quando binario,
 *  que vai em Base64.
 */

/** REQUEST:
 * POST / HTTP/1.1
 * Host: dynamodb.<region>.<domain>;
 * Accept-Encoding: identity
 * Content-Length: <PayloadSizeBytes>
 * User-Agent: <UserAgentString>
 * Content-Type: application/x-amz-json-1.0
 * Authorization: AWS4-HMAC-SHA256 Credential=<Credential>, SignedHeaders=<Headers>, Signature=<Signature>
 * X-Amz-Date: <Date>
 * X-Amz-Target: DynamoDB_20120810.GetItem
 *
 * {
 *     "TableName": "Pets",
 *     "Key": {
 *         "AnimalType": {"S": "Dog"},
 *         "Name": {"S": "Fido"}
 *     }
 * }
 */

/** RESPONSE:
 * HTTP/1.1 200 OK
 * x-amzn-RequestId: <RequestId>
 * x-amz-crc32: <Checksum>
 * Content-Type: application/x-amz-json-1.0
 * Content-Length: <PayloadSizeBytes>
 * Date: <Date>
 * {
 *     "Item": {
 *         "Age": {"N": "8"},
 *         "Colors": {
 *             "L": [
 *                 {"S": "White"},
 *                 {"S": "Brown"},
 *                 {"S": "Black"}
 *             ]
 *         },
 *         "Name": {"S": "Fido"},
 *         "Vaccinations": {
 *             "M": {
 *                 "Rabies": {
 *                     "L": [
 *                         {"S": "2009-03-17"},
 *                         {"S": "2011-09-21"},
 *                         {"S": "2014-07-08"}
 *                     ]
 *                 },
 *                 "Distemper": {"S": "2015-10-13"}
 *             }
 *         },
 *         "Breed": {"S": "Beagle"},
 *         "AnimalType": {"S": "Dog"}
 *     }
 * }
 */

/**
 * Para constantes tentativas com erros sempre implemente com algoritmo de recuo exponencial.
 */