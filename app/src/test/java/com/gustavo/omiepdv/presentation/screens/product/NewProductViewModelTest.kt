package com.gustavo.omiepdv.presentation.screens.product

import app.cash.turbine.test
import com.gustavo.omiepdv.domain.model.Product
import com.gustavo.omiepdv.domain.usecase.SaveProductUseCase
import com.gustavo.omiepdv.presentation.screens.MainDispatcherRule
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class NewProductViewModelTest {

    private val saveProductUseCase = mockk<SaveProductUseCase>()
    private val newProductViewModel = NewProductViewModel(saveProductUseCase)

    @Test
    fun saveProduct() {
        val product = Product(0, "name", "sasa", 10.0)

        every { saveProductUseCase.invoke(product) } returns true

        newProductViewModel.saveProduct(product)

        verify(exactly = 1) { saveProductUseCase.invoke(product) }
    }

    @Test
    fun shouldNotSaveInvalidProduct() {
        val product = Product()

        every { saveProductUseCase.invoke(product) } returns true

        newProductViewModel.saveProduct(product)

        verify(exactly = 1) { saveProductUseCase wasNot called }
    }

    @Test
    fun onDismiss() = runBlocking {
        newProductViewModel.onDismiss()

        newProductViewModel.newProductState.test {
            val state = awaitItem()
            assertFalse(state.showDialog)
        }
    }
}
