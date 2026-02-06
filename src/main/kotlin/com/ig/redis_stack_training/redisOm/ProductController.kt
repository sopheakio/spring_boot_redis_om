package com.ig.redis_stack_training.redisOm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/redis/product")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): Product? = productService.getById(id)

    @PostMapping
    fun addNewProduct(@RequestBody request: Product): Product = productService.addNew(request)

    @PutMapping
    fun updateProduct(@RequestBody request: Product): Product = productService.updateProduct(request)
}