package com.gustavo.omiepdv.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavo.omiepdv.R
import com.gustavo.omiepdv.domain.model.Product
import com.gustavo.omiepdv.presentation.extension.toCurrency
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutBottomSheet(
    onDismiss: () -> Unit,
    products: Map<Product, Int>,
    totalAmount: Double,
    calculatePrice: (Product, Int) -> Double,
    onConfirmCheckout: () -> Unit
) {
    val state =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })
    val coroutine = rememberCoroutineScope()
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxHeight()
            .statusBarsPadding()
            .navigationBarsPadding(),
        onDismissRequest = { onDismiss() },
        sheetState = state,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.checkout_review_sale),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            LazyColumn {
                items(products.toList()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = it.first.name)
                            Text(text = it.first.price.toCurrency())
                        }

                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = stringResource(R.string.checkout_total_amount))
                            Text(text = stringResource(R.string.checkout_item_multiplier, it.second))
                            Text(text = calculatePrice(it.first, it.second).toCurrency(), fontWeight = FontWeight.Bold)
                        }
                    }

                    Divider()
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.checkout_total_amount_value))
                Text(text = totalAmount.toCurrency())
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = WindowInsets.systemBars.getBottom(LocalDensity.current).dp),
                onClick = {
                    coroutine.launch {
                        state.hide()
                        onDismiss()
                    }
                    onConfirmCheckout()
                }
            ) {
                Text(text = stringResource(R.string.checkout_confirm_sale_button))
            }
        }

    }
}