package com.example.sephora.presentation.transformer


import com.example.sephora.mockResponse.ProductsResponseMock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class ProductTransformerTest {

    @InjectMocks
    private lateinit var productTransformer: ProductTransformer

    @Test
    fun `transformProductsToUiModel will return List of ProductUi`() {
        // When
        val result =
            productTransformer.transformProductsToUiModel(products = ProductsResponseMock.productsList)
        // Then
        assertThat(result).isEqualTo(ProductsResponseMock.productsUiList)
    }
}