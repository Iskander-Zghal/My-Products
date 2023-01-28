package com.example.sephora.data.model

data class Product(
    val idProduct: Long,
    val nameProduct: String,
    val imageProduct: String,
    val brandName: String,
    val productDescription: String,
    val price: Long,
)