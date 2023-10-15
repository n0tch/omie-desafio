package com.gustavo.omiepdv.presentation.screens.order

import app.cash.turbine.test
import com.gustavo.omiepdv.domain.model.Product
import com.gustavo.omiepdv.domain.usecase.CartCalculationUseCase
import com.gustavo.omiepdv.domain.usecase.ConfirmCheckoutUseCase
import com.gustavo.omiepdv.domain.usecase.GetProductsUseCase
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class NewOrderViewModelTest {

    private val cartCalculationUseCase: CartCalculationUseCase = mockk()
    private val getProductsUseCase: GetProductsUseCase = mockk()
    private val confirmCheckoutUseCase: ConfirmCheckoutUseCase = mockk()

    private val newOrderViewModel = NewOrderViewModel(
        getProductsUseCase,
        cartCalculationUseCase,
        confirmCheckoutUseCase
    )

    @Test
    fun fetchProducts() {

    }

    @Test
    fun onProductSelected() = runTest {
        val product = Product()
        val clientName = ""

        newOrderViewModel.onProductSelected(product, clientName)

        newOrderViewModel.productDetail.test {
            val productState = awaitItem()
            assertEquals(product, productState.product)
//            assertEquals(1, productState.quantity)
        }
    }

    @Test
    fun onProductAddedToCart() = runTest {
        val product = Product()
        val count = 2

        every { cartCalculationUseCase.validateCartItems(count, product, any()) } just runs
        every { cartCalculationUseCase.getTotalItemsCount(any()) } returns 10
        every { cartCalculationUseCase.getTotalAmount(any()) } returns 10.0

        newOrderViewModel.onProductAddedToCart(product, count)

        newOrderViewModel.newOrderState.test {
            val state = awaitItem()
            assertTrue(state.selectedProducts.isEmpty())
        }
    }

    @Test
    fun onProductSelectionDone() = runTest {
        newOrderViewModel.onProductSelectionDone()
        newOrderViewModel.orderStatusState.test {
            val state = awaitItem()
            assertFalse(state.showCheckout)
        }
    }

    @Test
    fun dismissEmptyCartModal() = runTest{
        newOrderViewModel.dismissEmptyCartModal()
        newOrderViewModel.orderStatusState.test {
            val state = awaitItem()
            assertFalse(state.cartEmpty)
        }
    }

    @Test
    fun dismissCheckoutModal() = runTest {
        newOrderViewModel.dismissCheckoutModal()
        newOrderViewModel.orderStatusState.test {
            val state = awaitItem()
            assertFalse(state.showCheckout)
        }
    }

    @Test
    fun confirmCheckout() = runTest {
        every { confirmCheckoutUseCase(any(), any()) } returns true

        newOrderViewModel.confirmCheckout()

        newOrderViewModel.orderStatusState.test {
            val state = awaitItem()
//            assertTrue(state.cartSaved)
        }
    }

    @Test
    fun calculatePrice() {
        val product = Product()
        every { cartCalculationUseCase.getTotalAmount(any()) } returns 12.0

        newOrderViewModel.calculatePrice(product, 2)
        verify(exactly = 1) { cartCalculationUseCase.getTotalAmount(mapOf(product to 2)) }
    }
}