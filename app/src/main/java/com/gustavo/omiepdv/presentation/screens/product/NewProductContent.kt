package com.gustavo.omiepdv.presentation.screens.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavo.omiepdv.R
import com.gustavo.omiepdv.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProductContent(
    onBack: () -> Unit = {},
    onSaveProduct: (Product) -> Unit = {},
    validationState: ProductValidationState
) {
    var name by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.add_product_title)) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {

                Text("Nome")
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    isError = !validationState.isNameValid,
                    trailingIcon = {
                        if (!validationState.isNameValid)
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                    }
                )
                Text(
                    text = stringResource(R.string.product_invalid_name),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.error.copy(
                        alpha = errorAlphaVisibility(
                            !validationState.isNameValid
                        )
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(stringResource(R.string.product_description))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = desc,
                    onValueChange = { desc = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    isError = !validationState.isDescriptionValid,
                    trailingIcon = {
                        if (!validationState.isDescriptionValid)
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                    }
                )
                Text(
                    text = stringResource(R.string.product_description_error),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.error.copy(
                        alpha = errorAlphaVisibility(!validationState.isDescriptionValid)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(stringResource(R.string.product_price))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = price,
                    maxLines = 1,
                    onValueChange = { price = it },
                    isError = !validationState.isPriceValid,
                    trailingIcon = {
                        if (!validationState.isPriceValid)
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )

                Text(
                    text = stringResource(R.string.product_price_error),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.error.copy(
                        alpha = errorAlphaVisibility(!validationState.isPriceValid)
                    )
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onSaveProduct(
                        Product(
                            name = name,
                            description = desc,
                            price = price.toDoubleOrNull() ?: -1.0
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.product_save_button_text))
            }
        }
    }
}

private fun errorAlphaVisibility(isError: Boolean): Float = if (isError) 1f else 0f
