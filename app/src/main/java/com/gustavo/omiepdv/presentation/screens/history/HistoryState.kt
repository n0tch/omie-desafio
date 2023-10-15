package com.gustavo.omiepdv.presentation.screens.history

import com.gustavo.omiepdv.domain.model.Order

data class HistoryState(
    val history: Map<Long, List<Order>> = emptyMap(),
    val isLoading: Boolean = false
)
