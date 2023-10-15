package com.gustavo.omiepdv.presentation.screens.order

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.omiepdv.domain.model.Product
import com.gustavo.omiepdv.domain.usecase.CartCalculationUseCase
import com.gustavo.omiepdv.domain.usecase.ConfirmCheckoutUseCase
import com.gustavo.omiepdv.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val cartCalculationUseCase: CartCalculationUseCase,
    private val confirmCheckoutUseCase: ConfirmCheckoutUseCase
) : ViewModel() {

    private var clientName: String = ""

    private val _newOrderState: MutableStateFlow<NewOrderState> = MutableStateFlow(NewOrderState())
    val newOrderState: StateFlow<NewOrderState> = _newOrderState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = NewOrderState()
    )

    private val _cartProductsState: SnapshotStateMap<Product, Int> = mutableStateMapOf()

    private val _orderAmountState: MutableStateFlow<OrderAmountState> = MutableStateFlow(OrderAmountState())

    val orderAmountState: StateFlow<OrderAmountState> = _orderAmountState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = OrderAmountState()
    )

    private val _orderStatusState: MutableStateFlow<OrderStatusState> = MutableStateFlow(OrderStatusState())
    val orderStatusState: StateFlow<OrderStatusState> = _orderStatusState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = OrderStatusState()
    )

    private val _productDetail: MutableStateFlow<ProductDetailState> =
        MutableStateFlow(ProductDetailState())

    val productDetail: StateFlow<ProductDetailState> = _productDetail.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ProductDetailState()
    )

    fun fetchProducts() {
        viewModelScope.launch {
            val products = getProductsUseCase()
            _newOrderState.update { it.copy(products = products) }
        }
    }

    fun onProductSelected(product: Product, clientName: String) {
        this.clientName = clientName
        _productDetail.update {
            it.copy(
                product = product,
                quantity = _cartProductsState[product] ?: 1
            )
        }
    }

    fun onProductAddedToCart(product: Product, count: Int) {
        cartCalculationUseCase.validateCartItems(count, product, _cartProductsState)

        _newOrderState.update {
            it.copy(
                selectedProducts = _cartProductsState,
            )
        }

        _orderAmountState.update {
            it.copy(
                productsCount = cartCalculationUseCase.getTotalItemsCount(_cartProductsState),
                totalAmount = cartCalculationUseCase.getTotalAmount(_cartProductsState)
            )
        }
    }

    fun onProductSelectionDone() {
        val emptyCart = _newOrderState.value.selectedProducts.isEmpty()
        _orderStatusState.update { it.copy(showCheckout = !emptyCart, cartEmpty = emptyCart) }
    }

    fun dismissEmptyCartModal() {
        _orderStatusState.update { it.copy(cartEmpty = false) }
    }

    fun dismissCheckoutModal(){
        _orderStatusState.update { it.copy(showCheckout = false) }
    }

    fun confirmCheckout() {
        val saved = confirmCheckoutUseCase(_newOrderState.value.selectedProducts, clientName)
        _orderStatusState.update { it.copy(cartSaved = saved) }
    }

    fun calculatePrice(product: Product, quantity: Int): Double {
        return cartCalculationUseCase.getTotalAmount(mapOf(product to quantity))
    }
}