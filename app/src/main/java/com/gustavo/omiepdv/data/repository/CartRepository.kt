package com.gustavo.omiepdv.data.repository

import com.gustavo.omiepdv.domain.model.Cart
import com.gustavo.omiepdv.domain.model.Product

interface CartRepository {
    fun fetchCart(): Map<Product, Int>
    fun saveCart(cart: Cart): Boolean
    fun clearCart()
}