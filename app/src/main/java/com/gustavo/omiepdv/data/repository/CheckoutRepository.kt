package com.gustavo.omiepdv.data.repository

import com.gustavo.omiepdv.domain.model.Product

interface CheckoutRepository {
    fun checkoutOrder(products: Map<Product, Int>, client: String): Boolean
}