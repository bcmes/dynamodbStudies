package com.github.bcmes.dynamodb.api.document;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Component
public class Aws03InsertItemJson {

    private DynamoDB dynamoDBClient;

    Aws03InsertItemJson(DynamoDB dynamoDBClient){
        this.dynamoDBClient = dynamoDBClient;
    }

    public void insertingItemsFromAJsonFile() throws IOException {
        JsonParser parser = new JsonFactory().createParser(new File("/home/c3po/Documents/projects/dynamodb/aws/data/moviedata.json"));
        JsonNode rootNode = new ObjectMapper().readTree(parser);
        Iterator<JsonNode> iteration = rootNode.iterator();
        ObjectNode currentNode;
        Table table = this.dynamoDBClient.getTable("Movies");

        while (iteration.hasNext()) {
            currentNode = (ObjectNode) iteration.next();
            int year = currentNode.path("year").asInt();
            String title = currentNode.path("title").asText();
            try {
                table.putItem(
                        new Item().withPrimaryKey("year", year, "title", title)
                                .withJSON("info", currentNode.path("info").toString())
                );

                System.out.println("PutItem succeeded: " + year + " " + title);
            }
            catch (Exception e) {
                System.err.println("Unable to add movie: " + year + " " + title);
                System.err.println(e.getMessage());
                break;
            }
        }

        parser.close();
    }
}
