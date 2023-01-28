package com.example.sephora.data.remote

import com.example.sephora.data.remote.ApiConstants.GET_PRODUCT_ENDPOINT
import retrofit2.http.GET

interface ApiService {
    @GET(GET_PRODUCT_ENDPOINT)
    suspend fun fetchAllProduct(): List<ProductResponse>
}