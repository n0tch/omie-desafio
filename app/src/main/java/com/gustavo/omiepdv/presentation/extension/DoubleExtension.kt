package com.gustavo.omiepdv.presentation.extension

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun Double.toCurrency(): String{
    val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        currency = Currency.getInstance(Locale.getDefault())
    }

    return formatter.format(this)
}