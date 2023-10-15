package com.gustavo.omiepdv.data.repository

import com.gustavo.omiepdv.data.datasource.OrdersLocalDataSource
import com.gustavo.omiepdv.data.datasource.ProductLocalDataSource
import com.gustavo.omiepdv.domain.model.OrderHistory
import com.gustavo.omiepdv.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue

class OrderRepositoryImplTest {

    private val ordersLocalDataSource = mockk<OrdersLocalDataSource>()
    private val productLocalDataSource = mockk<ProductLocalDataSource>()

    private val orderRepositoryImpl = OrderRepositoryImpl(ordersLocalDataSource, productLocalDataSource)

    @Test
    fun fetchOrderHistory() {
        every { productLocalDataSource.getProducts() } returns listOf(Product())
        every { ordersLocalDataSource.getOrders() } returns listOf(OrderHistory(1,2,"", 0))

        val output = orderRepositoryImpl.fetchOrderHistory()

        assertTrue(output.isNotEmpty())
    }

    @Test
    fun fetchOrderHistoryWithException() {
        every { productLocalDataSource.getProducts() } returns listOf(Product())
        every { ordersLocalDataSource.getOrders() } throws IllegalArgumentException()

        val output = orderRepositoryImpl.fetchOrderHistory()

        assertTrue(output.isEmpty())
    }
}
