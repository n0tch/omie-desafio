package com.gustavo.omiepdv.data.repository

import android.util.Log
import com.gustavo.omiepdv.data.datasource.ProductLocalDataSource
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource
) : ProductRepository {

    override fun fetchProducts(): List<Product> = try {
        productLocalDataSource.getProducts().sortedByDescending { it.id }
    } catch (exception: Exception) {
        emptyList()
    }

    override fun saveProduct(product: Product): Boolean = try {
        productLocalDataSource.saveProduct(product)
    } catch (exception: Exception) {
        Log.e(this::class.java.simpleName, exception.toString())
        false
    }
}
