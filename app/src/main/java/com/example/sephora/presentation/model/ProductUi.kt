package com.example.sephora.presentation.model


sealed class ProductsViewState {
    object Idle : ProductsViewState()
    data class Ready(val data: List<ProductUi>) : ProductsViewState()
    object Empty : ProductsViewState()
}

sealed class ProductDetailsViewState {
    object Idle : ProductDetailsViewState()
    data class Ready(val data: ProductUi) : ProductDetailsViewState()
}

data class ProductUi(
    val productId: Long,
    val productName: String,
    val productLogo: String,
    val brandName: String,
    val productDescription: String,
    val price: String,
)

