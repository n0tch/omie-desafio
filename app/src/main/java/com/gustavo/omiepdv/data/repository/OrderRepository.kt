package com.gustavo.omiepdv.data.repository

import com.gustavo.omiepdv.domain.model.Order

interface OrderRepository {

    fun fetchOrderHistory(): Map<Long, List<Order>>
}
