package com.gustavo.omiepdv.presentation.screens.product

data class ProductValidationState(
    val isNameValid: Boolean = true,
    val isDescriptionValid: Boolean = true,
    val isPriceValid: Boolean = true
){
    fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun isValidDescription(description: String): Boolean {
        return description.isNotEmpty()
    }

    fun isValidPrice(price: Double): Boolean {
        return price >= 0
    }

    fun isProductValid(): Boolean {
        return isNameValid && isDescriptionValid && isPriceValid
    }
}
