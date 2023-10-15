package com.gustavo.omiepdv.data.repository

import android.util.Log
import com.gustavo.omiepdv.data.datasource.OrdersLocalDataSource
import com.gustavo.omiepdv.data.datasource.ProductLocalDataSource
import com.gustavo.omiepdv.domain.model.Order
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val ordersLocalDataSource: OrdersLocalDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) : OrderRepository {

    override fun fetchOrderHistory(): Map<Long, List<Order>> = try {
        val products = productLocalDataSource.getProducts()
        ordersLocalDataSource
            .getOrders()
            .groupBy { it.date }
            .mapValues { map ->
                map.value.map { v ->
                    val p = products.find { it.id == v.productId } ?: Product()
                    Order(product = p, quantity = v.quantity, clientName = v.clientName ?: "")
                }
            }
    } catch (exception: Exception) {
        Log.e(this::class.java.simpleName, exception.toString())
        mapOf()
    }
}

