package com.example.sephora.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfootball.databinding.ProductItemBinding
import com.example.sephora.presentation.model.ProductUi
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val onItemClick: (ProductUi) -> Unit
) : ListAdapter<ProductUi, ProductAdapter.ProductViewHolder>(ProductsDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        binding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), itemClick = onItemClick
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ProductItemBinding, private val itemClick: (ProductUi) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productUi: ProductUi) {
            with(productUi) {
                Picasso.get().load(productLogo).into(binding.productImage)
                binding.productName.text = productName
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}

private class ProductsDiffUtilCallback : DiffUtil.ItemCallback<ProductUi>() {
    override fun areItemsTheSame(old: ProductUi, new: ProductUi): Boolean {
        return old === new
    }

    override fun areContentsTheSame(old: ProductUi, new: ProductUi): Boolean {
        return old.productId == new.productId
    }
}