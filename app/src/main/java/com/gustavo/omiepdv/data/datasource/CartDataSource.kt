package com.gustavo.omiepdv.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.gustavo.omiepdv.data.model.ProductCheckout
import com.gustavo.omiepdv.domain.model.Cart
import javax.inject.Inject

class CartDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    fun getCheckout(): List<ProductCheckout> {
        val storedCheckout = sharedPreferences.getStringSet(CHECKOUT_KEY, setOf())
        return storedCheckout?.map { gson.fromJson(it, ProductCheckout::class.java) } ?: emptyList()
    }

    fun saveCheckout(cart: Cart): Boolean {
        val productCheckout = cart.products.map { ProductCheckout(productId = it.key.id, quantity = it.value) }
        val productCheckoutJson = productCheckout.map { gson.toJson(it, ProductCheckout::class.java) }.toSet()

        sharedPreferences.edit {
            putStringSet(
                CHECKOUT_KEY,
                productCheckoutJson
            )
        }

        return true
    }

    fun clearCart() {
        sharedPreferences.edit { remove(CHECKOUT_KEY) }
    }

    companion object {
        const val CHECKOUT_KEY = "CHECKOUT_KEY"
    }
}