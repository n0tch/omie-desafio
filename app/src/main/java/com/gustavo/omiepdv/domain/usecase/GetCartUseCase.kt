package com.gustavo.omiepdv.domain.usecase

import com.gustavo.omiepdv.data.repository.CartRepository
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Map<Product, Int> = cartRepository.fetchCart()
}