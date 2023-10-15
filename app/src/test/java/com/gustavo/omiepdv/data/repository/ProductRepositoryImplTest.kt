package com.gustavo.omiepdv.data.repository

import com.gustavo.omiepdv.data.datasource.ProductLocalDataSource
import com.gustavo.omiepdv.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class ProductRepositoryImplTest {

    private val productLocalDataSource = mockk<ProductLocalDataSource>()
    private val productRepositoryImpl = ProductRepositoryImpl(productLocalDataSource)

    @Test
    fun fetchProducts() {
        every { productLocalDataSource.getProducts() } returns listOf(Product())

        val products = productRepositoryImpl.fetchProducts()
        assertTrue(products.isNotEmpty())
    }

    @Test
    fun fetchProductsWithException() {
        every { productLocalDataSource.getProducts() } throws IllegalStateException()

        val products = productRepositoryImpl.fetchProducts()
        assertTrue(products.isEmpty())
    }

    @Test
    fun saveProduct() {
        val product = Product()

        every { productLocalDataSource.saveProduct(product) } returns true

        val output = productRepositoryImpl.saveProduct(product)

        assertTrue(output)
    }

    @Test
    fun saveProductWithException() {
        val product = Product()

        every { productLocalDataSource.saveProduct(product) } throws IllegalStateException()

        val output = productRepositoryImpl.saveProduct(product)

        assertFalse(output)
    }
}