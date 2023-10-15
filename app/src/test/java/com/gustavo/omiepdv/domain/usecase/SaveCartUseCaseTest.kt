package com.gustavo.omiepdv.domain.usecase

import com.gustavo.omiepdv.data.repository.CartRepository
import com.gustavo.omiepdv.domain.model.Cart
import com.gustavo.omiepdv.domain.model.Product
import io.mockk.Called
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class SaveCartUseCaseTest{

    private val cartRepository = mockk<CartRepository>()
    private val saveCartUseCase = SaveCartUseCase(cartRepository)

    @Test
    fun saveCartShouldCallRepositoryOnce(){
        val products: Map<Product, Int> = mapOf(
            Product(id = 0, name = "", description = "", price = 10.0) to 2,
            Product(id = 1, name = "", description = "", price = 30.0) to 1
        )

        every { cartRepository.saveCart(Cart(products)) } returns true
        val saved = saveCartUseCase.invoke(products)

        verify(exactly = 1) {
            cartRepository.saveCart(Cart(products))
        }

        assertTrue(saved)
    }

    @Test
    fun saveCartShouldNotCallRepositoryWhenEmptyProducts(){
        val products: Map<Product, Int> = mapOf()

        val saved = saveCartUseCase.invoke(products)

        verify {
            cartRepository wasNot called
        }

        assertFalse(saved)
    }
}