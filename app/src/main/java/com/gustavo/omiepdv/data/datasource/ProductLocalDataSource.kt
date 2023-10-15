package com.gustavo.omiepdv.data.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    fun getProducts(): List<Product> {
        return sharedPreferences
            .getStringSet(PRODUCTS_KEY, setOf())
            ?.mapNotNull { gson.fromJson(it, Product::class.java) } ?: emptyList()
    }

    private fun getProductsAsJson(): Set<String> {
        return sharedPreferences.getStringSet(PRODUCTS_KEY, setOf()) ?: setOf()
    }

    fun saveProduct(product: Product): Boolean {
        val savedProducts = getProductsAsJson()
        val productJson = setOf(gson.toJson(product.copy(id = savedProducts.size), Product::class.java))

        val productsToSave = savedProducts + productJson

        sharedPreferences.edit().apply {
            putStringSet(PRODUCTS_KEY, productsToSave)
        }.apply()

        return true
    }

    companion object {
        const val PRODUCTS_KEY = "PRODUCTS_KEY"
    }
}