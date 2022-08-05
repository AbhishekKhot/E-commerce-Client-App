package com.example.codeboomecommerceapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.CategorisedProductItemBinding
import com.example.codeboomecommerceapp.model.Product

class CategoriesProductAdapter :
    RecyclerView.Adapter<CategoriesProductAdapter.CategoriesProductViewHolder>() {
    inner class CategoriesProductViewHolder(val binding: CategorisedProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesProductViewHolder {
        val binding = CategorisedProductItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return CategoriesProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        Glide.with(holder.itemView).load(product.product_cover_image)
            .placeholder(R.drawable.ic_image).into(holder.binding.ivProduct)
        holder.binding.tvProductName.text = product.product_name

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productID",product.product_id)
            it.findNavController().navigate(R.id.action_categoriesFragment_to_productDetailsFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}