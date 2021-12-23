package com.github.bcmes.dynamodb.api.lowlevel;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
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
