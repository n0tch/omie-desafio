package com.gustavo.omiepdv.presentation.screens.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gustavo.omiepdv.R

enum class HomeFeatures(@StringRes val text: Int, @DrawableRes val icon: Int) {
    HISTORY(R.string.home_history_title, R.drawable.ic_history),
    NEW_PRODUCT(R.string.home_new_product_title, R.drawable.ic_add_product),
    NEW_SALE(R.string.home_new_sale_title, R.drawable.ic_cart)
}