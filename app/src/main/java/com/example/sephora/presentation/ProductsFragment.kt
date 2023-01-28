package com.example.sephora.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myfootball.R
import com.example.myfootball.databinding.FragmentProductsBinding
import com.example.sephora.presentation.adapter.ProductAdapter
import com.example.sephora.presentation.model.ProductUi
import com.example.sephora.presentation.model.ProductsViewState
import com.example.sephora.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val productViewModel: ProductViewModel by activityViewModels()
    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val productAdapter = ProductAdapter(onItemClick = ::onItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel.getAllProducts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productsRecyclerView.adapter = productAdapter
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.productViewState.collect { state ->
                    when (state) {
                        ProductsViewState.Idle -> {
                            binding.animLoading.isVisible = true
                            binding.productsRecyclerView.isVisible = false
                            binding.animError.isVisible = false
                            binding.textViewError.isVisible = false
                        }
                        is ProductsViewState.Ready -> {
                            binding.animLoading.isVisible = false
                            binding.animError.isVisible = false
                            binding.textViewError.isVisible = false
                            binding.productsRecyclerView.isVisible = true
                            productAdapter.submitList(state.data)
                        }
                        is ProductsViewState.Empty -> {
                            binding.animLoading.isVisible = false
                            binding.productsRecyclerView.isVisible = false
                            binding.animError.isVisible = true
                            binding.textViewError.isVisible = true
                        }
                    }
                }
            }
        }
    }

    private fun onItemClick(productUi: ProductUi) {
        productViewModel.getProductDetails(productUi = productUi)
        findNavController().navigate(R.id.action_homeFragment_to_homeFragmentDetails)
    }
}