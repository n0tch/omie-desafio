package com.gustavo.omiepdv.domain.usecase

import com.gustavo.omiepdv.data.repository.CartRepository
import com.gustavo.omiepdv.data.repository.CheckoutRepository
import com.gustavo.omiepdv.domain.model.Product
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Test

class ConfirmCheckoutUseCaseTest {

    private val checkoutRepository = mockk<CheckoutRepository>()
    private val cartRepository = mockk<CartRepository>()

    private val confirmCheckoutUseCase = ConfirmCheckoutUseCase(checkoutRepository, cartRepository)

    @Test
    fun confirmCheckoutShouldCallCheckoutAndClearCart() {
        val products: Map<Product, Int> = mapOf(
            Product(id = 0, name = "", description = "", price = 10.0) to 2,
            Product(id = 1, name = "", description = "", price = 30.0) to 1
        )

        every { checkoutRepository.checkoutOrder(products, "") } returns true
        every { cartRepository.clearCart() } just runs

        confirmCheckoutUseCase(products, "")

        verify(exactly = 1) {
            checkoutRepository.checkoutOrder(products, "")
            cartRepository.clearCart()
        }
    }
}