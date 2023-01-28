package com.example.sephora.data.remote

import com.squareup.moshi.Json

data class ProductResponse(
    @Json(name = "product_id") val idProduct: Long,
    @Json(name = "product_name") val productName: String,
    @Json(name = "description") val description: String,
    @Json(name = "price") val price: Long,
    @Json(name = "images_url") val imagesUrl: ImageResponse,
    @Json(name = "c_brand") val brand: BrandResponse,
)

data class BrandResponse(
    @Json(name = "name") val name: String
)

data class ImageResponse(
    @Json(name = "small") val small: String
)
