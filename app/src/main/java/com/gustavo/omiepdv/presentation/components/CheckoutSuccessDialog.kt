package com.gustavo.omiepdv.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gustavo.omiepdv.R

@Composable
fun CheckoutSuccessDialog(onAction: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = {
                    onAction()
                }
            ) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        icon = { Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = null) },
        title = { Text(text = stringResource(R.string.checkout_success_message)) },
        text = { Text(text = stringResource(R.string.checkout_success_description)) }
    )
}