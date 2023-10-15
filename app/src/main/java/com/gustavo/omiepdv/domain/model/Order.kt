package com.gustavo.omiepdv.domain.model

data class Order(
    val clientName: String,
    val product: Product,
    val quantity: Int
)
