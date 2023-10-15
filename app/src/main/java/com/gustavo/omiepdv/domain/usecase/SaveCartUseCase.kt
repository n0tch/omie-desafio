package com.gustavo.omiepdv.domain.usecase

import com.gustavo.omiepdv.data.repository.CartRepository
import com.gustavo.omiepdv.domain.model.Cart
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class SaveCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(products: Map<Product, Int>): Boolean {
        return if (products.isEmpty()) {
            false
        } else {
            cartRepository.saveCart(Cart(products))
        }
    }
}