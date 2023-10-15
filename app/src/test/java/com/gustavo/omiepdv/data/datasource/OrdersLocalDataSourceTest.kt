package com.gustavo.omiepdv.data.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.gustavo.omiepdv.data.datasource.OrdersLocalDataSource.Companion.ORDER_CHECKOUT
import com.gustavo.omiepdv.domain.model.OrderHistory
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Before
import org.junit.Test

class OrdersLocalDataSourceTest {

    private val sharedPreferences = mockk<SharedPreferences>()
    private val gson = mockk<Gson>()
    private val editor = mockk<SharedPreferences.Editor>()
    private val ordersLocalDataSource = OrdersLocalDataSource(sharedPreferences, gson)

    @Before
    fun setup(){
        every { sharedPreferences.edit() } returns editor
        every { editor.apply() } just runs
    }

    @Test
    fun getOrders() {
        val orderHistory = OrderHistory(1, 1, "", 0)
        every { sharedPreferences.getStringSet(ORDER_CHECKOUT, setOf()) } returns setOf("")
        every { gson.fromJson("", OrderHistory::class.java) } returns orderHistory

        ordersLocalDataSource.getOrders()
    }
}