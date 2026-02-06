package com.ig.redis_stack_training.redisOm

import com.redis.om.spring.repository.RedisDocumentRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: RedisDocumentRepository<Product, String> {
    override fun findAll(): List<Product>
}