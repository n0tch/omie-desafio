package com.gustavo.omiepdv.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gustavo.omiepdv.R
import com.gustavo.omiepdv.domain.model.Product
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailBottomSheet(
    product: Product,
    quantitySelected: Int,
    onDismiss: () -> Unit,
    onProductAdded: (product: Product, quantity: Int) -> Unit
) {
    var quantity by remember { mutableIntStateOf(1) }
    val buttonTextRes: Int by remember { derivedStateOf { if(quantity == 0) R.string.cart_remove else R.string.cart_add} }
    val state = rememberModalBottomSheetState()
    val coroutine = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = state,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.cart_select_product_count),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }

        QuantitySelector(preSelectedQuantity = quantitySelected, onQuantityUpdated = { quantity = it })

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onProductAdded(product, quantity)
                coroutine.launch {
                    state.hide()
                    onDismiss()
                }
            }
        ) {
            Text(text = stringResource(id = buttonTextRes))
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}