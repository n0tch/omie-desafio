package com.gustavo.omiepdv.domain.usecase

import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.gustavo.omiepdv.domain.model.Order
import com.gustavo.omiepdv.domain.model.Product
import javax.inject.Inject

class CartCalculationUseCase @Inject constructor() {

    fun getTotalAmount(products: Map<Product, Int>): Double {
        return products.map { it.key.price * it.value }.sumOf { it }
    }

    fun getTotalOrderAmount(orders: List<Order>): Double {
        return orders.sumOf { it.quantity * it.product.price }
    }

    fun getTotalItemsCount(products: Map<Product, Int>): Int {
        return products.map { it.value }.sumOf { it }
    }

    fun validateCartItems(count: Int, product: Product, products: SnapshotStateMap<Product, Int>){
        if (count == 0 && products.containsKey(product)) {
            products.remove(product)
        } else if(count != 0) {
            products[product] = count
        }
    }

    fun validateCartSize(products: Map<Product, Int>): Boolean = products.isNotEmpty()
}