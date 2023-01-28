package com.example.sephora.domain.interactor

import com.example.sephora.data.repository.ProductRepositoryImpl
import com.example.sephora.util.Result.*
import javax.inject.Inject

class ProductsInteractor @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl,
) {
    internal suspend fun getAllProducts() =
        when (val result = productRepositoryImpl.fetchAllProducts()) {
            is Success -> result.data
            is Error -> null
        }
}
