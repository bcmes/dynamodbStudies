package com.github.bcmes.dynamodb.api.highlevel;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class Aws02CRUD {

    private final DynamoDBMapper dynamoDBMapperClient;

    Aws02CRUD(DynamoDBMapper dynamoDBMapperClient){
        this.dynamoDBMapperClient = dynamoDBMapperClient;
    }

    public void readOne(){
        Aws01MusicORM keySchema = new Aws01MusicORM();
        keySchema.setArtist("No One You Know");
        keySchema.setSongTitle("Call Me Today");

        try {
            Aws01MusicORM result = this.dynamoDBMapperClient.load(keySchema);
            if (result != null) {
                System.out.println("The song was released in "+ result.getAlbumTitle());
            } else {
                System.out.println("No matching song was found");
            }
        } catch (Exception e) {
            System.err.println("Unable to retrieve data: ");
            System.err.println(e.getMessage());
        }
    }

    public void readQuery(){
        Aws00ProductORM partitionKey = new Aws00ProductORM();
        partitionKey.setId(102);

        DynamoDBQueryExpression<Aws00ProductORM> queryExpression = new DynamoDBQueryExpression<Aws00ProductORM>()
                .withHashKeyValues(partitionKey);

        List<Aws00ProductORM> itemList = this.dynamoDBMapperClient.query(Aws00ProductORM.class, queryExpression);

        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(itemList.get(i).getTitle());
            System.out.println(itemList.get(i).getBookAuthors());
        }
    }

    public void save(){
        Aws00ProductORM item = new Aws00ProductORM();
        item.setId(102);
        item.setTitle("Book 102 Title");
        item.setISBN("222-2222222222");
        item.setBookAuthors(new HashSet<>(Arrays.asList("Author 1", "Author 2")));
        item.setSomeProp("Test");

        this.dynamoDBMapperClient.save(item); //save/merge
        //Por padrão, somente atributos correspondentes às propriedades de classe mapeadas são atualizados.
        //mapper.save(obj, new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.CLOBBER)); altera o comportamento, substituindo completamente o item.
        //a versão não precisará corresponder se a opção SaveBehavior.CLOBBER for usada.
    }

    public void deleteOne(){
        Aws00ProductORM item = new Aws00ProductORM();
        item.setId(102);
        this.dynamoDBMapperClient.delete(item);
    }
}

/**
 * DynamoDBMapper atua como um wrapper em torno do cliente de baixo nível (AmazonDynamoDB).
 * A classe DynamoDBMapper não permite criar, atualizar ou excluir estrutura de tabelas.
 */

/**
 * Demais metodos DynamoDBMapper: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/DynamoDBMapper.Methods.html
 save
 load
 delete
 query
 queryPage
 scan
 scanPage
 parallelScan
 batchSave
 batchLoad
 batchDelete
 batchWrite
 transactionWrite
 transactionLoad
 count
 generateCreateTableRequest
 createS3Link
 getS3ClientCache
 */

//Configurar a comunicacao com aws no java: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/CodeSamples.Java.html
