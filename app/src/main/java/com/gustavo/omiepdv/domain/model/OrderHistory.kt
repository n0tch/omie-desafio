package com.gustavo.omiepdv.domain.model

data class OrderHistory(
    val productId: Int,
    val quantity: Int,
    val clientName: String = "",
    val date: Long
)
