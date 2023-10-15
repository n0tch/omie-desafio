package com.gustavo.omiepdv.presentation.screens.order

import com.gustavo.omiepdv.domain.model.Product

data class ProductDetailState(
    val product: Product = Product(),
    val quantity: Int = 0,
)
