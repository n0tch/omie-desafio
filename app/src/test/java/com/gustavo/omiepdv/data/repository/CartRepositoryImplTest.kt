package com.gustavo.omiepdv.data.repository

import com.gustavo.omiepdv.data.datasource.CartDataSource
import com.gustavo.omiepdv.data.datasource.ProductLocalDataSource
import com.gustavo.omiepdv.data.model.ProductCheckout
import com.gustavo.omiepdv.domain.model.Cart
import com.gustavo.omiepdv.domain.model.Product
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class CartRepositoryImplTest {

    private val cartDataSource = mockk<CartDataSource>()
    private val productDataSource = mockk<ProductLocalDataSource>()

    private val cartRepositoryImpl = CartRepositoryImpl(cartDataSource, productDataSource)

    @Test
    fun saveCart() {
        val cart = Cart()

        every { cartDataSource.saveCheckout(cart) } returns true

        val output = cartRepositoryImpl.saveCart(cart)

        verify(exactly = 1) { cartDataSource.saveCheckout(cart) }
        assertTrue(output)
    }

    @Test
    fun saveCartWithException() {
        val cart = Cart()
        val exception = IllegalStateException()

        every { cartDataSource.saveCheckout(cart) } throws exception

        val output = cartRepositoryImpl.saveCart(cart)

        verify(exactly = 1) { cartDataSource.saveCheckout(cart) }
        assertFalse(output)
    }

    @Test
    fun fetchCart() {
        val products = listOf(Product())
        val productsCheckout = listOf(ProductCheckout(1,2))

        every { productDataSource.getProducts() } returns products
        every { cartDataSource.getCheckout() } returns productsCheckout

        val output = cartRepositoryImpl.fetchCart()

        println(output)

        verify(exactly = 1) {
            cartDataSource.getCheckout()
            productDataSource.getProducts()
        }

        assertTrue(output.isNotEmpty())
    }

    @Test
    fun fetchCartWithException() {
        every { productDataSource.getProducts() } throws IllegalStateException()

        val output = cartRepositoryImpl.fetchCart()

        verify(exactly = 1) {
            productDataSource.getProducts()
        }

        assertTrue(output.isEmpty())
    }

    @Test
    fun clearCart() {
        every { cartDataSource.clearCart() } just runs

        cartRepositoryImpl.clearCart()

        verify(exactly = 1) { cartDataSource.clearCart() }
    }
}