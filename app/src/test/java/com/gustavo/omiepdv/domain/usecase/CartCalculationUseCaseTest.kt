package com.gustavo.omiepdv.domain.usecase

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.gustavo.omiepdv.domain.model.Product
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class CartCalculationUseCaseTest {

    private val cartProductsUseCase = CartCalculationUseCase()

    @Test
    fun getTotalAmount() {
        val products: Map<Product, Int> = mapOf(
            Product(id = 0, name = "", description = "", price = 10.0) to 2,
            Product(id = 1, name = "", description = "", price = 30.0) to 1
        )

        val amount = cartProductsUseCase.getTotalAmount(products)

        assertEquals(50.0, amount)
    }

    @Test
    fun getTotalItemsCount() {
        val products: Map<Product, Int> = mapOf(
            Product(id = 0, name = "", description = "", price = 10.0) to 2,
            Product(id = 1, name = "", description = "", price = 30.0) to 1
        )

        val amount = cartProductsUseCase.getTotalItemsCount(products)

        assertEquals(3, amount)
    }

    @Test
    fun validateCartItemsToDoNotAddWhenCountIsZero() {
        val products: SnapshotStateMap<Product, Int> = mutableStateMapOf(
            Product(id = 0, name = "", description = "", price = 10.0) to 2,
            Product(id = 1, name = "", description = "", price = 30.0) to 1
        )

        cartProductsUseCase.validateCartItems(0, Product(), products)

        assertTrue(products.size == 2)
    }

    @Test
    fun validateCartItemsToRemoveIfCountIsZero() {
        val products: SnapshotStateMap<Product, Int> = mutableStateMapOf(
            Product(id = 0, name = "", description = "", price = 10.0) to 2,
            Product(id = 1, name = "", description = "", price = 30.0) to 1
        )

        cartProductsUseCase.validateCartItems(0, products.keys.first(), products)

        assertTrue(products.size == 1)
    }

    @Test
    fun validateCartSize() {
        val products: Map<Product, Int> = mapOf(
            Product(id = 0, name = "", description = "", price = 10.0) to 2,
            Product(id = 1, name = "", description = "", price = 30.0) to 1
        )
        val isValid = cartProductsUseCase.validateCartSize(products)
        assertTrue(isValid)
    }

    @Test
    fun validateEmptyCartSize() {
        val products: Map<Product, Int> = mapOf()
        val isValid = cartProductsUseCase.validateCartSize(products)
        assertFalse(isValid)
    }
}