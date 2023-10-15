package com.gustavo.omiepdv.di

import com.gustavo.omiepdv.data.repository.CartRepository
import com.gustavo.omiepdv.data.repository.CartRepositoryImpl
import com.gustavo.omiepdv.data.repository.CheckoutRepository
import com.gustavo.omiepdv.data.repository.CheckoutRepositoryImpl
import com.gustavo.omiepdv.data.repository.OrderRepository
import com.gustavo.omiepdv.data.repository.OrderRepositoryImpl
import com.gustavo.omiepdv.data.repository.ProductRepository
import com.gustavo.omiepdv.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun bindsProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindsCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindCheckoutRepository(checkoutRepositoryImpl: CheckoutRepositoryImpl): CheckoutRepository
}