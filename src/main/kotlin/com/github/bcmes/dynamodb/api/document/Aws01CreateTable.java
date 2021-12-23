package com.github.bcmes.dynamodb.api.document;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Aws01CreateTable {

    private DynamoDB dynamoDBClient;

    Aws01CreateTable(DynamoDB client){
        this.dynamoDBClient = client;
    }

    public void createTableWithSingleKey() throws InterruptedException {

        Table table = dynamoDBClient.createTable("ProductCatalog",
                Arrays.asList(new KeySchemaElement("Id", KeyType.HASH)),
                Arrays.asList(new AttributeDefinition("Id", ScalarAttributeType.N)),
                new ProvisionedThroughput(10L, 10L));

        table.waitForActive();

        System.out.println("Success. Table status: " + table.getDescription().getTableStatus());
    }

    public void createTableMoviesWithCompositeKey() throws InterruptedException {
            System.out.println("Attempting to create table; please wait...");

            Table table = this.dynamoDBClient.createTable("Movies",
                    Arrays.asList(
                            new KeySchemaElement("year", KeyType.HASH),
                            new KeySchemaElement("title", KeyType.RANGE)
                    ),
                    Arrays.asList(
                            new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)
                    ),
                    new ProvisionedThroughput(10L, 10L)
            );

            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
    }
}
