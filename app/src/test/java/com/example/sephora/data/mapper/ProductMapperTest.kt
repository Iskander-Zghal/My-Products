package com.example.sephora.data.mapper

import com.example.sephora.mockResponse.ProductsResponseMock.productsList
import com.example.sephora.mockResponse.ProductsResponseMock.productsResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class ProductMapperTest {

    @InjectMocks
    private lateinit var mapper: ProductMapper

    @Test
    fun `transformProductResponseToProduct when should return list of products`() {
        // When
        val result =
            mapper.transformProductResponseToProduct(response = productsResponse)

        // Then
        assertThat(result).isEqualTo(productsList)
    }
}