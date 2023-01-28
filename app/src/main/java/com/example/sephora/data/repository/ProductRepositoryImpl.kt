package com.example.sephora.data.repository

import com.example.sephora.data.mapper.ProductMapper
import com.example.sephora.data.model.Product
import com.example.sephora.data.remote.ApiService
import com.example.sephora.domain.repository.ProductRepository
import javax.inject.Inject
import com.example.sephora.util.Result

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productMapper: ProductMapper,
) : ProductRepository {
    override suspend fun fetchAllProducts(): Result<List<Product>> {
        return try {
            val leagueResponse = apiService.fetchAllProduct()
            if (leagueResponse.isNotEmpty()) {
                productMapper.transformProductResponseToProduct(leagueResponse).let { data ->
                    Result.Success(data = data)
                }
            } else Result.Error("No results found.")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An error occurred.")
        }
    }
}