package com.github.bcmes.dynamodb.api.highlevel;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="ProductCatalog")
public class Aws00ProductORM {

    private Integer id;
    private String title;
    private String ISBN;
    private Set<String> bookAuthors;
    private String someProp;

    @DynamoDBHashKey(attributeName="Id")
    public Integer getId() { return id; } //A propriedade deve ser uma string escalar, número ou binários.
    public void setId(Integer id) {this.id = id; }

    @DynamoDBAttribute(attributeName="Title")
    public String getTitle() {return title; }
    public void setTitle(String title) { this.title = title; }

    @DynamoDBAttribute(attributeName="ISBN")
    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    @DynamoDBAttribute(attributeName="Authors")
    public Set<String> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(Set<String> bookAuthors) { this.bookAuthors = bookAuthors; }

    @DynamoDBIgnore
    public String getSomeProp() { return someProp; }
    public void setSomeProp(String someProp) { this.someProp = someProp; }
}

/**
 * Por padrão, as propriedades da classe são mapeadas para os atributos com o mesmo nome.
 * Anotacoes disponiveis para ORM: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/DynamoDBMapper.Annotations.html
 */

/**
 * O Amazon DynamoDB oferece suporte aos seguintes tipos de dados Java.
 *
 *     String
 *     Boolean, boolean
 *     Byte, byte
 *     Date (como a string de precisão em milissegundos ISO_8601, modificada para UTC)
 *     Calendar (como a string de precisão em milissegundos ISO_8601, modificada para UTC)
 *     Long, long
 *     Integer, int
 *     Double, double
 *     Float, float
 *     BigDecimal
 *     BigInteger
 *     SET
 *     LIST
 *     MAP
 */

/** No Dynamo e mapeado para:
 * Todos os tipos de número = N
 * Strings = S
 * Booliano = BOOL
 * ByteBuffer = B
 * Data = S (tipo String). Os valores de Date são armazenados como strings formatadas em ISO-8601.
 * Colecoes = SS (conjunto de strings), NS (conjunto de números) e BS (conjunto de binários).
 *
 * Quando nao ha mapeamento para um tipo diretamente, use: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/DynamoDBMapper.ArbitraryDataMapping.html
 */

/**
 * interface DynamoDBTypeConverter permite que você mapeie seus próprios tipos de dados arbitrários
 *  em um tipo de dados com suporte nativo do DynamoDB.
 *  fonte: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/DynamoDBMapper.ArbitraryDataMapping.html
 */