package com.gustavo.omiepdv.presentation.screens.order

import com.gustavo.omiepdv.domain.model.Product

data class NewOrderState(
    val products: List<Product> = emptyList(),
    val selectedProducts: Map<Product, Int> = mapOf(),
)

data class OrderAmountState(
    val productsCount: Int = 0,
    val totalAmount: Double = 0.0
)

data class OrderStatusState(
    val cartEmpty: Boolean = false,
    val cartSaved: Boolean = false,
    val showCheckout: Boolean = false,
)