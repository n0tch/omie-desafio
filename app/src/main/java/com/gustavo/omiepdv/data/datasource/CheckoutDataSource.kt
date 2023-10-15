package com.gustavo.omiepdv.data.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.gustavo.omiepdv.domain.model.OrderHistory
import javax.inject.Inject

class CheckoutDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    fun getSavedOrders(): Set<String> {
        return sharedPreferences.getStringSet(ORDER_CHECKOUT, setOf()) ?: setOf()
    }

    fun saveOrderHistory(orderHistory: List<OrderHistory>){
        val ordersJson = orderHistory.map { gson.toJson(it, OrderHistory::class.java) }
        val save = getSavedOrders() + ordersJson
        sharedPreferences.edit().apply {
            putStringSet(ORDER_CHECKOUT, save)
        }.apply()
    }

    companion object {
        const val ORDER_CHECKOUT = "ORDER_CHECKOUT"
    }
}