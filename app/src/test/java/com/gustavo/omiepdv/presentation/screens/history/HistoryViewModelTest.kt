package com.gustavo.omiepdv.presentation.screens.history

import app.cash.turbine.test
import com.gustavo.omiepdv.domain.model.Order
import com.gustavo.omiepdv.domain.model.Product
import com.gustavo.omiepdv.domain.usecase.CartCalculationUseCase
import com.gustavo.omiepdv.domain.usecase.GetOrderHistoryUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class HistoryViewModelTest {

    private val getOrderHistoryUseCase = mockk<GetOrderHistoryUseCase>()
    private val cartCalculationUseCase = mockk<CartCalculationUseCase>()
    private val historyViewModel = HistoryViewModel(getOrderHistoryUseCase, cartCalculationUseCase)

    @Test
    fun getHistoryStateInitialState() = runBlocking {
        val products = mapOf<Long, List<Order>>()

        every { getOrderHistoryUseCase.invoke() } returns products

        historyViewModel.fetchOrderHistory()

        historyViewModel.historyState.test {
            val history = awaitItem()

            assertTrue(history.history.isEmpty())
            assertFalse(history.isLoading)
        }
    }

    @Test
    fun getHistoryState() = runBlocking {
        val products = mapOf<Long, List<Order>>()

        every { getOrderHistoryUseCase.invoke() } returns products

        historyViewModel.fetchOrderHistory()

        historyViewModel.historyState.test {
            val history = awaitItem()
            assertEquals(history.history, products)
            assertFalse(history.isLoading)
        }
    }

    @Test
    fun fetchOrderHistory() {
        every { getOrderHistoryUseCase.invoke() } returns mapOf()
        historyViewModel.fetchOrderHistory()

        verify(exactly = 1) { getOrderHistoryUseCase.invoke() }
    }

    @Test
    fun calculateOrderAmount() {
        val orders = listOf(Order(clientName = "", Product(price = 20.0), 1))
        every { historyViewModel.calculateOrderAmount(orders) } returns 20.0
        val output = historyViewModel.calculateOrderAmount(orders)

        verify(exactly = 1) { cartCalculationUseCase.getTotalOrderAmount(orders) }
        assertEquals(20.0, output)
    }
}