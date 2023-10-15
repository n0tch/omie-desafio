package com.gustavo.omiepdv.domain.usecase

import com.gustavo.omiepdv.data.repository.ProductRepository
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    operator fun invoke(): List<Product> = productRepository.fetchProducts()
}
