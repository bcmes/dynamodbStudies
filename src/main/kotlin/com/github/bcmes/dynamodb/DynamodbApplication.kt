package com.github.bcmes.dynamodb

import com.github.bcmes.dynamodb.api.document.Aws01CreateTable
import com.github.bcmes.dynamodb.api.document.Aws02DeleteTable
import com.github.bcmes.dynamodb.api.document.Aws04CRUD
import com.github.bcmes.dynamodb.api.highlevel.Aws02CRUD
import com.github.bcmes.dynamodb.api.lowlevel.Aws01CRUD
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication
class DynamodbApplication

fun main(args: Array<String>) {
	//get the context
	val context = SpringApplicationBuilder(DynamodbApplication::class.java)
		.web(WebApplicationType.NONE)
		.run(*args)
	//get the bean by type
	val bean = context.getBean(Aws01CreateTable::class.java)
	//operations with the context
	bean.createTableWithSingleKey();

}
