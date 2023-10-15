package com.gustavo.omiepdv.presentation.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.omiepdv.domain.model.Order
import com.gustavo.omiepdv.domain.usecase.CartCalculationUseCase
import com.gustavo.omiepdv.domain.usecase.GetOrderHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase,
    private val cartCalculationUseCase: CartCalculationUseCase
) : ViewModel() {

    private val _historyState: MutableStateFlow<HistoryState> =
        MutableStateFlow(HistoryState(isLoading = true))
    val historyState: StateFlow<HistoryState> = _historyState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HistoryState())

    fun fetchOrderHistory() {
        val history = getOrderHistoryUseCase()
        _historyState.update {
            it.copy(
                history = history,
                isLoading = false
            )
        }
    }

    fun calculateOrderAmount(orders: List<Order>): Double {
        return cartCalculationUseCase.getTotalOrderAmount(orders)
    }

}