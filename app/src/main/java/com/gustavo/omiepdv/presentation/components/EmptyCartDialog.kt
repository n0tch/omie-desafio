package com.gustavo.omiepdv.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gustavo.omiepdv.R

@Composable
fun EmptyCartDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        text = { Text(text = stringResource(R.string.cart_empty_description)) }
    )
}