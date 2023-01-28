package com.example.sephora.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myfootball.R
import com.example.myfootball.databinding.FragmentProductDetailsBinding
import com.example.sephora.presentation.model.ProductDetailsViewState
import com.example.sephora.presentation.model.ProductUi
import com.example.sephora.util.viewBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private val sharedProductViewModel: ProductViewModel by activityViewModels()
    private val binding by viewBinding(FragmentProductDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedProductViewModel.productDetailsViewState.collect { state ->
                    when (state) {
                        ProductDetailsViewState.Idle -> Unit
                        is ProductDetailsViewState.Ready -> {
                            displayProductDetails(state.data)
                            println(state.data)
                        }
                    }
                }
            }
        }
    }

    private fun displayProductDetails(productUi: ProductUi) {
        with(productUi) {
            with(binding) {
                Picasso.get().load(productLogo).into(productImageView)
                productNameTextView.text = getString(R.string.product_name, productName)
                priceTextView.text = getString(R.string.product_price, price)
                descriptionTextView.text =
                    getString(R.string.product_description, productDescription)
                bannerTextView.text = getString(R.string.product_brand, brandName)
            }
        }
    }
}