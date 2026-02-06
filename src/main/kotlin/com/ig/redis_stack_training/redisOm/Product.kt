package com.ig.redis_stack_training.redisOm

import com.redis.om.spring.annotations.Document
import com.redis.om.spring.annotations.Indexed
import org.springframework.data.annotation.Id

@Document(value = "product")
data class Product(
    @Id
    @Indexed
    var id: String,
    var sku: String,
    var name: String
)
