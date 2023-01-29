package com.example.sephora.data.repository

import com.example.sephora.data.mapper.ProductMapper
import com.example.sephora.data.remote.ApiService
import com.example.sephora.mockResponse.ProductsResponseMock.productsList
import com.example.sephora.mockResponse.ProductsResponseMock.productsResponse
import com.example.sephora.util.Result
import com.example.sephora.util.Result.Success
import com.example.sephora.util.lenientGiven
import com.example.sephora.util.willReturn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
internal class ProductRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var productMapper: ProductMapper

    @InjectMocks
    private lateinit var productRepositoryImpl: ProductRepositoryImpl

    private val scheduler = TestCoroutineScheduler()
    private val mainDispatcher = StandardTestDispatcher(scheduler)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(mainDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchAllProducts() = runTest {
        //Given
        lenientGiven(apiService.fetchAllProduct()).willReturn(productsResponse)
        lenientGiven(productMapper.transformProductResponseToProduct(productsResponse)).willReturn(
            productsList
        )

        // When
        val result = productRepositoryImpl.fetchAllProducts()

        // Then
        then(productMapper).should().transformProductResponseToProduct(response = productsResponse)

        assertThat(result).isEqualTo(Success(productsList))
    }

    @Test
    fun `fetchAllProducts - result will be return Error`() = runTest {
        // Given
        lenientGiven(apiService.fetchAllProduct()).willReturn(emptyList())

        // When
        val result = productRepositoryImpl.fetchAllProducts()

        // Then
        assertThat(result).isEqualTo(Result.Error("No results found.", null))

    }
}