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

    @GetMapping("/all")
    fun getAll(): List<Product> = productService.getAll()

    @GetMapping("/search/name")
    fun searchByName(@RequestParam name: String): List<Product> {
        return productService.searchByName(name)
    }

    @GetMapping("/search/sku")
    fun searchBySku(@RequestParam sku: String): List<Product> {
        return productService.searchBySku(sku)
    }
}