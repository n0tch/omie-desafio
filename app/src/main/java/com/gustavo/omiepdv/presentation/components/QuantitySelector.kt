package com.gustavo.omiepdv.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gustavo.omiepdv.R

private enum class QuantityOperation {
    INCREASE, DECREASE
}
@Composable
fun QuantitySelector(preSelectedQuantity: Int = 1, onQuantityUpdated: (Int) -> Unit) {
    var quantityCount by remember {  mutableIntStateOf(preSelectedQuantity) }

    val updateQuantity: (QuantityOperation) -> Unit = { operation ->
        if(operation == QuantityOperation.DECREASE && quantityCount > 0){
            quantityCount--
        } else if(operation == QuantityOperation.INCREASE){
            quantityCount++
        }

        onQuantityUpdated(quantityCount)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.secondary, CircleShape),
            onClick = { updateQuantity(QuantityOperation.DECREASE) },
            enabled = quantityCount > 0
        ) {
            Icon(painterResource(id = R.drawable.ic_minus), contentDescription = null, modifier = Modifier.size(18.dp))
        }

        Text(
            modifier = Modifier.padding(horizontal = 28.dp),
            text = "$quantityCount",
            style = MaterialTheme.typography.titleLarge
        )

        IconButton(
            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.secondary, CircleShape),
            onClick = { updateQuantity(QuantityOperation.INCREASE) }
        ) {
            Icon(painterResource(id = R.drawable.ic_plus), contentDescription = null, modifier = Modifier.size(18.dp))
        }
    }
}

@Preview
@Composable
fun QuantitySelectorPreview() {
    QuantitySelector{}
}