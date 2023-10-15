package com.gustavo.omiepdv.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.omiepdv.domain.usecase.SalesReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val salesReportUseCase: SalesReportUseCase,
) : ViewModel() {

    private val _saleState: MutableStateFlow<SaleState> = MutableStateFlow(SaleState())
    val saleState: StateFlow<SaleState> = _saleState
        .asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SaleState()
        )

    fun fetchSaleTotalValues() {
        val saleReport = salesReportUseCase()
        _saleState.update {
            it.copy(
                totalSaleValue = saleReport.totalSaleValue,
                totalSalesCount = saleReport.totalSalesCount
            )
        }
    }
}