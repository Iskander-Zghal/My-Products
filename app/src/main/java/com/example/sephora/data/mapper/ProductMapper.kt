package com.example.sephora.data.mapper

import com.example.sephora.data.model.Product
import com.example.sephora.data.remote.ProductResponse
import javax.inject.Inject

class ProductMapper @Inject constructor() {
    internal fun transformProductResponseToProduct(response: List<ProductResponse>) =
        response.map {
            with(it) {
                Product(
                    idProduct = idProduct,
                    nameProduct = productName,
                    imageProduct = imagesUrl.small,
                    brandName = brand.name,
                    productDescription = description,
                    price = price
                )
            }
        }
}