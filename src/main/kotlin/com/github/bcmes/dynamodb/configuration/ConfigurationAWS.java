package com.github.bcmes.dynamodb.configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationAWS {

    @Value("${aws.service.endpoint}")
    private String serviceEndpoint;
    @Value("${aws.signing.region}")
    private String signingRegion;

    @Bean
    public DynamoDB getClientDynamoDB(){
        return new DynamoDB(getClientAmazonDynamoDB());
    }

    @Bean
    public AmazonDynamoDB getClientAmazonDynamoDB(){
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                            this.serviceEndpoint,
                            this.signingRegion
                    )
                ).build();
    }

    @Bean
    public DynamoDBMapper getDynamoDBMapperClient(){
        return new DynamoDBMapper(getClientAmazonDynamoDB());
    }
}
/**
 * O DynamoDBMapper pode receber um segundo parametro customizando seu comportamente,
 *  veja em: https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/DynamoDBMapper.OptionalConfig.html
 *  , ha formas de carregamento como *LAZY_LOADING, EAGER_LOADING, ITERATION_ONLY e outros recursos.
 *  , ha formas de salvar como *UPDATE, CLOBBER e outros recursos.
 *  , ha formas de ignorar o nome de tabela especificado pela anotação DynamoDBTable
 *      de uma classe e usar a tabela informada no momento, entre outros recursos.
 */
