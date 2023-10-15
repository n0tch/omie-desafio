package com.gustavo.omiepdv.data.mapper

import com.gustavo.omiepdv.data.model.ProductCheckout
import com.gustavo.omiepdv.domain.model.Product

fun List<ProductCheckout>.toProductMap(products: List<Product>): Map<Product, Int> {
    return associateBy(
        { stored -> products.find { it.id == stored.productId } ?: Product() },
        { it.quantity }
    )
}
