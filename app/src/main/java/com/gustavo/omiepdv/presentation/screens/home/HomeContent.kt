package com.gustavo.omiepdv.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavo.omiepdv.R
import com.gustavo.omiepdv.presentation.extension.toCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    salesState: SaleState,
    onFeatureClicked: (HomeFeatures) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeHeader(salesState.totalSaleValue, salesState.totalSalesCount)

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 6.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = HomeFeatures.values(),
                span = { item ->
                    val gridSpan = if (item == HomeFeatures.NEW_SALE) 2 else 1
                    GridItemSpan(gridSpan)
                }
            ) { feature ->
                Card(modifier = Modifier.size(134.dp), onClick = { onFeatureClicked(feature) }) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .aspectRatio(1F),
                            painter = painterResource(id = feature.icon),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(id = feature.text),
                            modifier = Modifier.padding(horizontal = 24.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHeader(totalSaleValue: Double, totalSalesCount: Int) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_point_of_sale),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = stringResource(R.string.home_logo_name),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.inversePrimary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = stringResource(R.string.home_total_sales),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.inversePrimary.copy(0.5f)
        )

        Text(
            text = totalSaleValue.toCurrency(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("$totalSalesCount ")
                }

                append(stringResource(R.string.home_sale))
            },
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.inversePrimary.copy(0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

