package com.example.sephora.domain.interactor

import com.example.sephora.data.repository.ProductRepositoryImpl
import com.example.sephora.mockResponse.ProductsResponseMock
import com.example.sephora.util.Result.Error
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
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
internal class ProductsInteractorTest {
    @Mock
    lateinit var productRepositoryImpl: ProductRepositoryImpl

    @InjectMocks
    private lateinit var intercator: ProductsInteractor

    private val scheduler = TestCoroutineScheduler()
    private val mainDispatcher = StandardTestDispatcher(scheduler)

    @BeforeEach
    internal fun setUp() {
        Dispatchers.setMain(mainDispatcher)
    }

    @AfterEach
    internal fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllProducts - when fetchAllProducts fetchAllProducts is Success - will return products `() =
        runTest {
            lenientGiven(productRepositoryImpl.fetchAllProducts()).willReturn(
                Success(
                    ProductsResponseMock.productsList
                )
            )
            // When
            val result = intercator.getAllProducts()

            // Then
            BDDMockito.then(productRepositoryImpl).should().fetchAllProducts()
            Assertions.assertThat(result).isEqualTo(ProductsResponseMock.productsList)
        }

    @Test
    fun `getAllProducts - when fetchAllProducts fetchAllProducts is Error - will return null`() =
        runTest {
            lenientGiven(productRepositoryImpl.fetchAllProducts()).willReturn(
                Error(message = "not found")
            )
            // When
            val result = intercator.getAllProducts()

            // Then
            BDDMockito.then(productRepositoryImpl).should().fetchAllProducts()
            Assertions.assertThat(result).isEqualTo(null)
        }
}