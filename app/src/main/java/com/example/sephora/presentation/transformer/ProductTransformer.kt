package com.example.sephora.presentation.transformer

import com.example.sephora.data.model.Product
import com.example.sephora.presentation.model.ProductUi
import javax.inject.Inject

class ProductTransformer @Inject constructor() {

    internal fun transformProductsToUiModel(products: List<Product>) =
        products.map { transformProductsToUiModel(it) }

    private fun transformProductsToUiModel(product: Product) = with(product) {
        ProductUi(
            productId = idProduct,
            productName = nameProduct,
            productLogo = imageProduct,
            brandName = brandName,
            productDescription = productDescription,
            price = price.toString()
        )
    }
}
