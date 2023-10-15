package com.gustavo.omiepdv.domain.model

data class Cart(
    val products: Map<Product, Int> = emptyMap()
)
