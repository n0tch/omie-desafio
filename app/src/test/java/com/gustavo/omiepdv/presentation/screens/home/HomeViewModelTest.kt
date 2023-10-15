package com.gustavo.omiepdv.presentation.screens.home

import app.cash.turbine.test
import com.gustavo.omiepdv.domain.model.SaleReport
import com.gustavo.omiepdv.domain.usecase.SalesReportUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class HomeViewModelTest {

    private val salesReportUseCase = mockk<SalesReportUseCase>()
    private val homeViewModel = HomeViewModel(salesReportUseCase)

    @Test
    fun getSaleStateWithCorrectValues() = runBlocking {
        val saleReport = SaleReport(2.0, 10)
        every { salesReportUseCase() } returns saleReport

        homeViewModel.fetchSaleTotalValues()

        homeViewModel.saleState.test {
            val state = awaitItem()
            assertEquals(0.0, state.totalSaleValue)
        }
    }

    @Test
    fun fetchSaleTotalValues() {
        val saleReport = SaleReport(2.0, 10)
        every { salesReportUseCase.invoke() } returns saleReport

        homeViewModel.fetchSaleTotalValues()

        verify(exactly = 1) { salesReportUseCase.invoke() }
    }
}