package com.example.sephora.presentation

import com.example.sephora.domain.interactor.ProductsInteractor
import com.example.sephora.mockResponse.ProductsResponseMock
import com.example.sephora.presentation.model.ProductDetailsViewState
import com.example.sephora.presentation.model.ProductUi
import com.example.sephora.presentation.model.ProductsViewState
import com.example.sephora.presentation.transformer.ProductTransformer
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
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
internal class ProductViewModelTest {

    private val scheduler = TestCoroutineScheduler()
    private val mainDispatcher = StandardTestDispatcher(scheduler)

    @Mock
    lateinit var productsInteractor: ProductsInteractor

    @Mock
    lateinit var productTransformer: ProductTransformer

    lateinit var viewModel: ProductViewModel

    @BeforeEach
    internal fun setUp() {
        viewModel = ProductViewModel(
            productsInteractor = productsInteractor,
            productTransformer = productTransformer,
            dispatcher = mainDispatcher
        )
        Dispatchers.setMain(mainDispatcher)
    }

    @AfterEach
    internal fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllProducts - when interactor return productsList  - return  productsUiList`() =
        runTest {
            // Given
            lenientGiven(productsInteractor.getAllProducts()).willReturn(ProductsResponseMock.productsList)
            lenientGiven(productTransformer.transformProductsToUiModel(products = ProductsResponseMock.productsList)).willReturn(
                ProductsResponseMock.productsUiList
            )
            // When
            viewModel.getAllProducts()

            //Then
            scheduler.advanceUntilIdle()
            assertThat(viewModel.productViewState.value).isEqualTo(
                ProductsViewState.Ready(ProductsResponseMock.productsUiList)
            )
        }

    @Test
    fun `getAllProducts - when interactor return null - return  Empty State`() = runTest {
        // Given
        lenientGiven(productsInteractor.getAllProducts()).willReturn(null)

        // When
        viewModel.getAllProducts()

        //Then
        scheduler.advanceUntilIdle()
        assertThat(viewModel.productViewState.value).isEqualTo(
            ProductsViewState.Empty
        )
    }


    @Test
    fun getProductDetails() = runTest {
        // When
        viewModel.getProductDetails(productUi = ProductsResponseMock.productsUiList.first())

        //Then
        scheduler.advanceUntilIdle()
        assertThat(viewModel.productDetailsViewState.value).isEqualTo(
            ProductDetailsViewState.Ready(
                data =
                ProductUi(
                    productId = 1461267310,
                    productName = "Size Up - Mascara Volume Extra Large Immédiat",
                    productDescription = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
                    price = "140",
                    productLogo = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
                    brandName = "SEPHORA COLLECTION"
                )
            )
        )
    }
}