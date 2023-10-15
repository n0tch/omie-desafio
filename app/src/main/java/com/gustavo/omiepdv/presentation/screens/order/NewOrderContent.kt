package com.gustavo.omiepdv.presentation.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavo.omiepdv.R
import com.gustavo.omiepdv.domain.model.Product
import com.gustavo.omiepdv.presentation.extension.toCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderComponent(
    newOrderState: NewOrderState,
    amountState: OrderAmountState,
    onAddProduct: () -> Unit = {},
    onAddProductToCart: (product: Product, clientName: String) -> Unit = { _,_ ->},
    onCheckoutClicked: () -> Unit = {},
    navigateBack: () -> Unit
) {
    var clientName by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.order_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            CheckoutBottomBar(
                productsCount = amountState.productsCount,
                totalAmount = amountState.totalAmount,
                onCheckoutClicked = onCheckoutClicked
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddProduct() }) {
                Row(
                    Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.order_add_product))
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 12.dp)
                .fillMaxSize()
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = clientName,
                onValueChange = { clientName = it },
                maxLines = 1,
                singleLine = true,
                label = { Text(text = stringResource(R.string.order_client_name)) }
            )

            Text(
                text = stringResource(R.string.order_select_products),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center
            )

            LazyColumn(
                modifier = Modifier,
            ) {
                items(newOrderState.products) { product ->
                    ProductItem(
                        product = product,
                        productQuantity = newOrderState.selectedProducts[product] ?: 0,
                        onAddProductToCart = { onAddProductToCart(product, clientName) }
                    )
                }

                item { Spacer(modifier = Modifier.padding(44.dp)) }
            }
        }
    }
    if(newOrderState.products.isEmpty()){
        ProductsEmptyState()
    }
}

@Composable
private fun ProductsEmptyState() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Filled.Warning,
            contentDescription = null
        )
        Text(text = stringResource(R.string.order_no_products_title), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Text(text = stringResource(R.string.order_no_products_description), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductItem(
    product: Product,
    productQuantity: Int,
    onAddProductToCart: (product: Product) -> Unit = { }
) {
    Card(
        onClick = { onAddProductToCart(product) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = product.price.toCurrency(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    product.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (productQuantity > 0) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(text = stringResource(R.string.order_product_count, productQuantity), fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun CheckoutBottomBar(
    productsCount: Int,
    totalAmount: Double,
    onCheckoutClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Text(text = stringResource(R.string.order_count_products, productsCount), fontSize = 12.sp)
            Text(
                text = totalAmount.toCurrency(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(onClick = { onCheckoutClicked() }) {
            Text(stringResource(R.string.order_confirm))
        }
    }
}