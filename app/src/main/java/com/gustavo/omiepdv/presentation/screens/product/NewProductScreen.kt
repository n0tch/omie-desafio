package com.gustavo.omiepdv.presentation.screens.product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NewProductScreen(navigateBack: () -> Unit = {}) {

    val viewModel: NewProductViewModel = hiltViewModel()
    val newProductState by viewModel.newProductState.collectAsStateWithLifecycle()
    val productValid by viewModel.productValidationState.collectAsStateWithLifecycle()

    NewProductContent(
        onBack = navigateBack,
        onSaveProduct = { product ->
            viewModel.saveProduct(product)
        },
        validationState = productValid
    )

    if (newProductState.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismiss() },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onDismiss()
                        navigateBack()
                    }
                ) {
                    Text(text = "Ok")
                }
            },
            icon = { Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = null) },
            title = { Text(text = "Sucesso!") },
            text = { Text(text = "O produto informado foi cadastrado com sucesso!") }
        )
    }
}