package com.example.sephora.domain.repository

import com.example.sephora.data.model.Product
import com.example.sephora.util.Result

interface ProductRepository {
    suspend fun fetchAllProducts():  Result<List<Product>>
}