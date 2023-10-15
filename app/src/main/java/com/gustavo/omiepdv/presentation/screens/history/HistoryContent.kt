package com.gustavo.omiepdv.presentation.screens.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavo.omiepdv.R
import com.gustavo.omiepdv.domain.model.Order
import com.gustavo.omiepdv.presentation.extension.toCurrency
import com.gustavo.omiepdv.presentation.extension.toDate

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    historyState: HistoryState,
    onBackPressed: () -> Unit = {},
    calculateTotal: (List<Order>) -> Double
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.history_title))
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    ) {
        LazyColumn(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            historyState.history.forEach { (date, orders) ->
                stickyHeader {
                    HistoryHeader(
                        date.toDate(),
                        calculateTotal(orders).toCurrency(),
                        orders.firstOrNull()?.clientName ?: ""
                    )
                }
                itemsIndexed(orders) { index, order ->
                    HistoryItem(index, order)
                }
            }
        }

        if (historyState.history.isEmpty()) {
            HistoryEmptyState()
        }
    }
}

@Composable
fun HistoryEmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = null,
            modifier = Modifier.size(42.dp)
        )
        Text(
            text = stringResource(R.string.history_empty_text),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HistoryHeader(
    text: String,
    leftText: String,
    clientName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = leftText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = stringResource(
                R.string.history_client_name,
                clientName.ifEmpty { stringResource(R.string.history_client_not_identified) }),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}

@Composable
private fun HistoryItem(index: Int, order: Order, modifier: Modifier = Modifier) {
    val alpha: Float = if (index % 2 == 0) 0.6f else 0.4f
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = alpha))
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(R.string.history_product, order.product.name),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(horizontal = 14.dp)
            )

            Text(
                text = order.product.price.toCurrency(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(horizontal = 14.dp)
            )

            Text(
                text = stringResource(R.string.history_unit_total_amount) + (order.product.price * order.quantity).toCurrency(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(horizontal = 14.dp)
            )
        }

        Text(
            text = "x${order.quantity}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(horizontal = 14.dp)
        )
    }
}
