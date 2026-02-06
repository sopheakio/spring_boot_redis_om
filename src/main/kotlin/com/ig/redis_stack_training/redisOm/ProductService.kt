package com.ig.redis_stack_training.redisOm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService {

    @Autowired
    lateinit var productRepository: ProductRepository

    fun getById(id: String): Product? = productRepository.findByIdOrNull(id)

    @Transactional
    fun addNew(request: Product): Product = productRepository.save(request)

    @Transactional
    fun updateProduct(request: Product): Product {
        val product = productRepository.findById(request.id).get()
        product.sku = request.sku
        product.name = request.name
        return productRepository.save(product)
    }
}