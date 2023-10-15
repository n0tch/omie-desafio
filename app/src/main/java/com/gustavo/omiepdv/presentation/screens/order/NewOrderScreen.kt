package com.gustavo.omiepdv.presentation.screens.order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gustavo.omiepdv.presentation.components.CheckoutBottomSheet
import com.gustavo.omiepdv.presentation.components.CheckoutSuccessDialog
import com.gustavo.omiepdv.presentation.components.EmptyCartDialog
import com.gustavo.omiepdv.presentation.components.ProductDetailBottomSheet

@Composable
fun NewOrderScreen(
    onAddProduct: () -> Unit,
    navigateBack: () -> Unit
) {

    val viewModel: NewOrderViewModel = hiltViewModel()
    val newOrderState by viewModel.newOrderState.collectAsStateWithLifecycle()
    val productDetail by viewModel.productDetail.collectAsStateWithLifecycle()
    val amountState by viewModel.orderAmountState.collectAsStateWithLifecycle()
    val orderStatusState by viewModel.orderStatusState.collectAsStateWithLifecycle()

    var showProductDetailModal by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.fetchProducts()
    }

    NewOrderComponent(
        newOrderState = newOrderState,
        amountState = amountState,
        onAddProduct = onAddProduct,
        onAddProductToCart = { product, clientName ->
            viewModel.onProductSelected(product, clientName)
            showProductDetailModal = true
        },
        onCheckoutClicked = {
            viewModel.onProductSelectionDone()
        },
        navigateBack = navigateBack
    )

    if (showProductDetailModal) {
        ProductDetailBottomSheet(
            product = productDetail.product,
            quantitySelected = productDetail.quantity,
            onDismiss = { showProductDetailModal = false },
            onProductAdded = viewModel::onProductAddedToCart
        )
    }

    if (orderStatusState.cartSaved) {
        CheckoutSuccessDialog(onAction = navigateBack)
    }

    if (orderStatusState.cartEmpty) {
        EmptyCartDialog(onDismiss = viewModel::dismissEmptyCartModal)
    }

    if (orderStatusState.showCheckout) {
        CheckoutBottomSheet(
            products = newOrderState.selectedProducts,
            onConfirmCheckout = viewModel::confirmCheckout,
            totalAmount = amountState.totalAmount,
            calculatePrice = viewModel::calculatePrice,
            onDismiss = viewModel::dismissCheckoutModal
        )
    }
}
