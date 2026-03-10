package com.ig.redis_stack_training.redisOm

import com.redis.om.spring.annotations.*
import org.springframework.data.annotation.Id

@Document(value = "product")
@IndexingOptions(indexName = "idx:product", creationMode = IndexCreationMode.DROP_AND_RECREATE)
data class Product(
    @Id
    @Indexed
    var id: String,
    @Indexed
    var sku: String,
    @Searchable
    var name: String,
    var price: Double?
)
