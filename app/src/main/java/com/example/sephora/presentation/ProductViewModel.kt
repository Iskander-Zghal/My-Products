package com.example.sephora.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sephora.IoDispatcher
import com.example.sephora.domain.interactor.ProductsInteractor
import com.example.sephora.presentation.model.ProductDetailsViewState
import com.example.sephora.presentation.model.ProductUi
import com.example.sephora.presentation.model.ProductsViewState
import com.example.sephora.presentation.transformer.ProductTransformer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.sephora.util.Result

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productsInteractor: ProductsInteractor,
    private val productTransformer: ProductTransformer,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private var _productViewState =
        MutableStateFlow<ProductsViewState>(value = ProductsViewState.Idle)
    val productViewState: StateFlow<ProductsViewState> = _productViewState

    private var _productDetailsViewState =
        MutableStateFlow<ProductDetailsViewState>(value = ProductDetailsViewState.Idle)
    val productDetailsViewState: StateFlow<ProductDetailsViewState> = _productDetailsViewState

    fun getAllProducts() {
        viewModelScope.launch(dispatcher) {
            productsInteractor.getAllProducts()?.let {
                _productViewState.value = ProductsViewState.Ready(
                    data = productTransformer.transformProductsToUiModel(products = it)
                )
            } ?: run {
                _productViewState.value = ProductsViewState.Empty
            }
        }
    }

    fun getProductDetails(productUi: ProductUi) {
        _productDetailsViewState.value = ProductDetailsViewState.Ready(
            data = productUi
        )
    }
}





