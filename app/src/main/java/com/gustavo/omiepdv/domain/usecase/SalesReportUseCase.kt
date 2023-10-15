package com.gustavo.omiepdv.domain.usecase

import com.gustavo.omiepdv.data.repository.OrderRepository
import com.gustavo.omiepdv.domain.model.SaleReport
import javax.inject.Inject

class SalesReportUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    operator fun invoke(): SaleReport {
        val orders = orderRepository.fetchOrderHistory().values.flatten()
        return SaleReport(
            totalSaleValue = orders.sumOf { it.product.price * it.quantity },
            totalSalesCount = orders.sumOf { it.quantity }
        )
    }
}