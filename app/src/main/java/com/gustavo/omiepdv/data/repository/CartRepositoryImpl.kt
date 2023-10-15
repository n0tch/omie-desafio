package com.gustavo.omiepdv.data.repository

import android.util.Log
import com.gustavo.omiepdv.data.datasource.CartDataSource
import com.gustavo.omiepdv.data.datasource.ProductLocalDataSource
import com.gustavo.omiepdv.data.mapper.toProductMap
import com.gustavo.omiepdv.domain.model.Cart
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) : CartRepository {

    override fun saveCart(cart: Cart): Boolean = try {
        cartDataSource.saveCheckout(cart)
    } catch (exception: Exception) {
        false
    }

    override fun fetchCart(): Map<Product, Int> = try {
        val products = productLocalDataSource.getProducts()
        cartDataSource.getCheckout().toProductMap(products)
    } catch (exception: Exception) {
        mapOf()
    }

    override fun clearCart() = try {
        cartDataSource.clearCart()
    } catch (exception: Exception) {
        Log.e(this::class.java.simpleName, exception.toString())
        Unit
    }
}