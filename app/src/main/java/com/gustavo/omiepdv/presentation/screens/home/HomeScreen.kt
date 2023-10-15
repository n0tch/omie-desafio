package com.gustavo.omiepdv.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    onNavigateToFeature: (HomeFeatures) -> Unit = {}
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val salesState by viewModel.saleState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.fetchSaleTotalValues()
    }

    HomeContent(
        salesState = salesState,
        onFeatureClicked = onNavigateToFeature
    )
}