package com.gustavo.omiepdv.domain.usecase

import com.gustavo.omiepdv.data.repository.CartRepository
import com.gustavo.omiepdv.data.repository.CheckoutRepository
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class ConfirmCheckoutUseCase@Inject constructor(
    private val checkoutRepository: CheckoutRepository,
    private val cartRepository: CartRepository
) {

    operator fun invoke(products: Map<Product, Int>, clientName: String): Boolean {
        return checkoutRepository.checkoutOrder(products, clientName)
            .also { saved -> if (saved) cartRepository.clearCart() }
    }
}