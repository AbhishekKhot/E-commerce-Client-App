package com.example.codeboomecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.SearchItemBinding
import com.example.codeboomecommerceapp.model.Product
import com.example.codeboomecommerceapp.util.ProductAdapterOnItemClickListener

class ProductSearchAdapter(val itemClickListener: ProductAdapterOnItemClickListener) : RecyclerView.Adapter<ProductSearchAdapter.ProductSearchViewHolder>() {

    inner class ProductSearchViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    val differCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSearchViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductSearchViewHolder, position: Int) {
        val product = differ.currentList[position]
        Glide.with(holder.itemView).load(product.product_cover_image)
            .placeholder(R.drawable.ic_image).into(holder.binding.ivProduct)
        holder.binding.tvName.text = product.product_name
        holder.binding.tvPrice.text = product.product_selling_price

        holder.binding.ivProduct.setOnClickListener {
            itemClickListener.viewDetails(product.product_id.toString())
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}

