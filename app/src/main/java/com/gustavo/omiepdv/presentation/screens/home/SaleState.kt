package com.gustavo.omiepdv.presentation.screens.home

data class SaleState(
    val totalSaleValue: Double = 0.0,
    val totalSalesCount: Int = 0,
    val exception: Exception? = null
)