package com.gustavo.omiepdv.data.repository

import android.util.Log
import com.gustavo.omiepdv.data.datasource.CheckoutDataSource
import com.gustavo.omiepdv.domain.model.OrderHistory
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(
    private val checkoutDataSource: CheckoutDataSource
) : CheckoutRepository {

    override fun checkoutOrder(products: Map<Product, Int>, client: String): Boolean = try {
        val currentDate = System.currentTimeMillis()
        val orderList = products.map {
            OrderHistory(it.key.id, it.value, client, currentDate)
        }
        checkoutDataSource.saveOrderHistory(orderList)
        true
    } catch (exception: Exception) {
        Log.e(this::class.java.simpleName, exception.toString())
        false
    }
}