package com.github.bcmes.dynamodb.configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
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
}
