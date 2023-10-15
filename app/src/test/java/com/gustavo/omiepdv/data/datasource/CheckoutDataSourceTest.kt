package com.gustavo.omiepdv.data.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.gustavo.omiepdv.domain.model.OrderHistory
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class CheckoutDataSourceTest {

    private val sharedPreferences = mockk<SharedPreferences>()
    private val gson = mockk<Gson>()
    private val editor = mockk<SharedPreferences.Editor>()
    private val checkoutDataSource = CheckoutDataSource(sharedPreferences, gson)

    @Before
    fun setup() {
        every { sharedPreferences.edit() } returns editor
        every { editor.apply() } just runs
    }

    @Test
    fun whenGetSavedOrders() {
        val expectedSet = setOf("1", "2", "3")
        every { sharedPreferences.getStringSet(CheckoutDataSource.ORDER_CHECKOUT, setOf()) } returns expectedSet

        val output = checkoutDataSource.getSavedOrders()

        verify(exactly = 1) { sharedPreferences.getStringSet(CheckoutDataSource.ORDER_CHECKOUT, setOf()) }
        assertEquals(expectedSet, output)
    }

    @Test
    fun saveOrderHistory() {
        val orderHistory = listOf(OrderHistory(1, 1, "", 0))

        every { editor.putStringSet(CheckoutDataSource.ORDER_CHECKOUT, setOf("")) } returns editor
        every { sharedPreferences.getStringSet(CheckoutDataSource.ORDER_CHECKOUT, setOf()) } returns setOf("")
        every { gson.toJson(any(), OrderHistory::class.java) } returns ""

        checkoutDataSource.saveOrderHistory(orderHistory)

        verify(exactly = 1) { editor.putStringSet(CheckoutDataSource.ORDER_CHECKOUT, setOf("")) }
    }
}