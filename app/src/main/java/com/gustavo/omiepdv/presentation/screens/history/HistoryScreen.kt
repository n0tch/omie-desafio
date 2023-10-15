package com.gustavo.omiepdv.presentation.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gustavo.omiepdv.presentation.components.LoadingIndicator

@Composable
fun HistoryScreen(
    onBackPressed: () -> Unit = {}
) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val historyState by viewModel.historyState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.fetchOrderHistory()
    }

    if (historyState.isLoading) {
        LoadingIndicator()
    } else {
        HistoryContent(
            historyState = historyState, onBackPressed,
            calculateTotal = { viewModel.calculateOrderAmount(it) }
        )
    }
}

