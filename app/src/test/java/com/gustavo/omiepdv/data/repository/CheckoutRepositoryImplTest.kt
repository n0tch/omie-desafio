package com.gustavo.omiepdv.data.repository

import com.gustavo.omiepdv.data.datasource.CheckoutDataSource
import com.gustavo.omiepdv.domain.model.Product
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class CheckoutRepositoryImplTest {

    private val checkoutDataSource = mockk<CheckoutDataSource>()
    private val checkoutRepositoryImpl = CheckoutRepositoryImpl(checkoutDataSource)

    @Test
    fun checkoutOrder() {
        val products = mapOf<Product, Int>()

        every { checkoutDataSource.saveOrderHistory(any()) } just runs
        val output = checkoutRepositoryImpl.checkoutOrder(products, "")

        assertTrue(output)
    }

    @Test
    fun checkoutOrderWithException() {
        val products = mapOf<Product, Int>()

        every { checkoutDataSource.saveOrderHistory(any()) } throws IllegalArgumentException()
        val output = checkoutRepositoryImpl.checkoutOrder(products, "")

        assertFalse(output)
    }
}