package com.gustavo.omiepdv.mock

import com.gustavo.omiepdv.data.model.ProductCheckout

object ProductCheckoutMock {
    fun list() = listOf(
        ProductCheckout(productId = 0, quantity = 1),
        ProductCheckout(productId = 2, quantity = 1),
        ProductCheckout(productId = 3, quantity = 4)
    )

    fun singleItem() = ProductCheckout(productId = 0, quantity = 1)
}