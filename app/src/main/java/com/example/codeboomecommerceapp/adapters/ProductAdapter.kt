package com.example.codeboomecommerceapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.ProductItemBinding
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.db.SavedProduct
import com.example.codeboomecommerceapp.model.Product
import com.example.codeboomecommerceapp.util.ProductAdapterOnItemClickListener

class ProductAdapter(val itemClickListener: ProductAdapterOnItemClickListener) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ProductItemBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            tvProductName.text = product.product_name
            tvProductPrice.text = "$" + product.product_selling_price
        }
        Glide.with(holder.itemView).load(product.product_cover_image)
            .placeholder(R.drawable.ic_image).into(holder.binding.ivProduct)


        holder.binding.tvAddToCart.setOnClickListener {
            val data = SavedProduct(
                product.product_id.toString(),
                product.product_name,
                product.product_cover_image,
                product.product_selling_price,
                product.product_description
            )
            itemClickListener.addToWishList(data,it)
            it.setBackgroundColor(Color.RED)
        }

        holder.binding.ivProduct.setOnClickListener {
            itemClickListener.viewDetails(product.product_id.toString())
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
