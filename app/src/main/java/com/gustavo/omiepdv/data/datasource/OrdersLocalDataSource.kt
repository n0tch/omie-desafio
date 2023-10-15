package com.gustavo.omiepdv.data.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.gustavo.omiepdv.domain.model.OrderHistory
import javax.inject.Inject

class OrdersLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    fun getOrders(): List<OrderHistory> {
        val orders = sharedPreferences.getStringSet(ORDER_CHECKOUT, setOf()) ?: setOf()
        return orders.map { gson.fromJson(it, OrderHistory::class.java) }
    }

    companion object {
        const val ORDER_CHECKOUT = "ORDER_CHECKOUT"
    }
}