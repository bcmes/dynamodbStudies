package com.github.bcmes.dynamodb.api.document;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.stereotype.Component;

@Component
public class Aws02DeleteTable {

    private DynamoDB dynamoDBClient;

    Aws02DeleteTable(DynamoDB dynamoDBClient){
        this.dynamoDBClient = dynamoDBClient;
    }

    public void deleteTable(){
        Table table = this.dynamoDBClient.getTable("Movies");

        try {
            System.out.println("Attempting to delete table; please wait...");
            table.delete();
            table.waitForDelete();
            System.out.print("Success.");
        }
        catch (Exception e) {
            System.err.println("Unable to delete table: ");
            System.err.println(e.getMessage());
        }
    }
}
