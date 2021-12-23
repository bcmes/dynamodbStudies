package com.github.bcmes.dynamodb.api.document;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.*;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class Aws04CRUD {

    private DynamoDB dynamoDBClient;

    Aws04CRUD(DynamoDB dynamoDBClient){
        this.dynamoDBClient = dynamoDBClient;
    }

    public void insertItem(){
        Table table = this.dynamoDBClient.getTable("Movies");
        int year = 2015;
        String title = "The Big New Movie";
        final Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("plot", "Nothing happens at all.");
        infoMap.put("rating", 0);

        try {
            System.out.println("Adding a new item...");

            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("year", year, "title", title)
                            .withMap("info", infoMap)
                    );

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void readItem(){
        Table table = this.dynamoDBClient.getTable("Movies");
        int year = 2015;
        String title = "The Big New Movie";
        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey("year", year, "title", title);

        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            System.out.println("GetItem succeeded: " + outcome);

        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void updateItem() {
        Table table = this.dynamoDBClient.getTable("Movies");
        int year = 2015;
        String title = "The Big New Movie";

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("year", year, "title", title)
                .withUpdateExpression("set info.rating = :r, info.plot=:p, info.actors=:a")
                .withValueMap(new ValueMap()
                        .withNumber(":r", 5.5)
                        .withString(":p", "Everything happens all at once.")
                        .withList(":a", Arrays.asList("Larry", "Moe", "Curly"))
                )
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void updateAtomicCountItem() {
        Table table = this.dynamoDBClient.getTable("Movies");
        int year = 2015;
        String title = "The Big New Movie";

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("year", year, "title", title)
                .withUpdateExpression("set info.rating = info.rating + :val")
                .withValueMap(new ValueMap().withNumber(":val", 1))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Incrementing an atomic counter...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void updateConditionalItem() {
        Table table = this.dynamoDBClient.getTable("Movies");
        int year = 2015;
        String title = "The Big New Movie";

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(new PrimaryKey("year", year, "title", title))
                .withUpdateExpression("remove info.actors[0]")
                .withConditionExpression("size(info.actors) >= :num")
                .withValueMap(new ValueMap().withNumber(":num", 3))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Attempting a conditional update...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void deleteItem() {
        Table table = this.dynamoDBClient.getTable("Movies");
        int year = 2015;
        String title = "The Big New Movie";

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("year", year, "title", title))
                .withConditionExpression("info.rating >= :val")
                .withValueMap(new ValueMap().withNumber(":val", 5.0));

        try {
            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void readQuery() {
        Table table = this.dynamoDBClient.getTable("Movies");
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#yr", "year");
        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":yyyy", 1985);

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#yr = :yyyy")
                .withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;

        try {
            System.out.println("Movies from 1985");
            items = table.query(querySpec);
            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getNumber("year") + ": " + item.getString("title"));
            }
        }
        catch (Exception e) {
            System.err.println("Unable to query movies from 1985");
            System.err.println(e.getMessage());
        }

        //Query 02
        valueMap.put(":yyyy", 1992);
        valueMap.put(":letter1", "A");
        valueMap.put(":letter2", "L");

        querySpec.withProjectionExpression("#yr, title, info.genres, info.actors[0]")
                .withKeyConditionExpression("#yr = :yyyy and title between :letter1 and :letter2")
                .withNameMap(nameMap) //nameMap fornece substituição de nome. Isso é usado porque year é uma palavra reservada no DynamoDB.
                .withValueMap(valueMap); //valueMap fornece substituição de valor. Ele é usado porque não é possível usar literais em nenhuma expressão.

        try {
            System.out.println("\nMovies from 1992 - titles A-L, with genres and lead actor");
            items = table.query(querySpec);
            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getNumber("year") + ": " + item.getString("title") + " " + item.getMap("info"));
            }
        }
        catch (Exception e) {
            System.err.println("Unable to query movies from 1992:");
            System.err.println(e.getMessage());
        }
    }

    public void readScan() {
        Table table = this.dynamoDBClient.getTable("Movies");
        ScanSpec scanSpec = new ScanSpec()
                .withProjectionExpression("#yr, title, info.rating")
                .withFilterExpression("#yr between :start_yr and :end_yr") //filtro é aplicado somente depois que a tabela inteira foi interada.
                .withNameMap(new NameMap().with("#yr", "year"))
                .withValueMap(new ValueMap()
                        .withNumber(":start_yr", 1950)
                        .withNumber(":end_yr", 1959)
                );

        try {
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            Iterator<Item> iter = items.iterator();
            while (iter.hasNext()) {
                Item item = iter.next();
                System.out.println(item.toString());
            }
        }
        catch (Exception e) {
            System.err.println("Unable to scan the table:");
            System.err.println(e.getMessage());
        }
    }
}
