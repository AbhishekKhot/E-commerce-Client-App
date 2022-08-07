package com.example.codeboomecommerceapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.CartItemBinding
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.ui.ProductViewModel
import com.example.codeboomecommerceapp.util.CartAdapterOnItemClickListener
import com.google.android.material.snackbar.Snackbar

class CartProductsAdapter(val itemClickListener: CartAdapterOnItemClickListener) : RecyclerView.Adapter<CartProductsAdapter.CartProductViewHolder>() {

    inner class CartProductViewHolder(val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    val diffCallback = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.tvProductName.text = product.productName
        holder.binding.tvProductPrice.text = "$" + product.productSellingPrice
        Glide.with(holder.itemView).load(product.productImage).placeholder(R.drawable.ic_image).into(holder.binding.ivProduct)

        holder.binding.tvDelete.setOnClickListener {
            val data=ProductModel(product.productId,product.productName,product.productImage,product.productSellingPrice)
            itemClickListener.deleteItem(data,it)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}