package com.gustavo.omiepdv.presentation.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.omiepdv.domain.model.Product
import com.gustavo.omiepdv.domain.usecase.SaveProductUseCase
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
class NewProductViewModel @Inject constructor(
    private val saveProductUseCase: SaveProductUseCase,
) : ViewModel() {

    private val _newProductState: MutableStateFlow<NewProductState> =
        MutableStateFlow(NewProductState())
    val newProductState: StateFlow<NewProductState> = _newProductState
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NewProductState())

    private val _productValidationState: MutableStateFlow<ProductValidationState> =
        MutableStateFlow(ProductValidationState())
    val productValidationState: StateFlow<ProductValidationState> = _productValidationState
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProductValidationState())

    fun saveProduct(product: Product) {
        _productValidationState.update {
            it.copy(
                isNameValid = it.isNameValid(product.name),
                isDescriptionValid = it.isValidDescription(product.description),
                isPriceValid = it.isValidPrice(product.price)
            )
        }

        if (_productValidationState.value.isProductValid()) {
            val saved = saveProductUseCase(product)
            _newProductState.update { it.copy(showDialog = saved) }
        }
    }

    fun onDismiss() {
        _newProductState.update { it.copy(showDialog = false) }
    }
}
