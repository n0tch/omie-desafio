package com.gustavo.omiepdv.data.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.gustavo.omiepdv.data.model.ProductCheckout
import com.gustavo.omiepdv.domain.model.Cart
import com.gustavo.omiepdv.domain.model.Product
import com.gustavo.omiepdv.mock.ProductCheckoutMock
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CartDataSourceTest {

    private val sharedPreferences = mockk<SharedPreferences>()
    private val gson = mockk<Gson>()
    private val editor = mockk<SharedPreferences.Editor>()
    private val cartDataSource = CartDataSource(sharedPreferences = sharedPreferences, gson = gson)

    @Before
    fun setUp() {
        every { gson.fromJson("", ProductCheckout::class.java) } returns ProductCheckoutMock.singleItem()

        every { sharedPreferences.edit() } returns editor
        every { editor.apply() } just runs
    }

    @Test
    fun getCheckout() {
        every { sharedPreferences.getStringSet(CartDataSource.CHECKOUT_KEY, setOf()) } returns setOf("")
        cartDataSource.getCheckout()
        verify(exactly = 1) { sharedPreferences.getStringSet(CartDataSource.CHECKOUT_KEY, setOf()) }
    }

    @Test
    fun saveCheckout() {
        val cart = Cart(mapOf(Product(1) to 1))

        every { gson.toJson(any(), ProductCheckout::class.java) } returns ""
        every { editor.putStringSet(CartDataSource.CHECKOUT_KEY, setOf("")) } returns editor
        cartDataSource.saveCheckout(cart)
    }

    @Test
    fun clearCart() {
        every { editor.remove(CartDataSource.CHECKOUT_KEY) } returns editor
        cartDataSource.clearCart()
        verify(exactly = 1) { editor.remove(CartDataSource.CHECKOUT_KEY) }
    }
}