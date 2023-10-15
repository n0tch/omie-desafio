package com.gustavo.omiepdv.data.repository

import com.gustavo.omiepdv.domain.model.Product

interface ProductRepository {

    fun fetchProducts(): List<Product>

    fun saveProduct(product: Product): Boolean
}