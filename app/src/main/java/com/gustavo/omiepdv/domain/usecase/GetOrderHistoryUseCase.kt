package com.gustavo.omiepdv.domain.usecase

import com.gustavo.omiepdv.data.repository.OrderRepository
import com.gustavo.omiepdv.domain.model.Order
import javax.inject.Inject

class GetOrderHistoryUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(): Map<Long, List<Order>> = orderRepository.fetchOrderHistory()
}
