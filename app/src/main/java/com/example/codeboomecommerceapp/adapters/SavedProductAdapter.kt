package com.example.codeboomecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.SavedItemBinding
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.db.SavedProduct
import com.example.codeboomecommerceapp.util.ProductAdapterOnItemClickListener

class SavedProductAdapter(val itemClickListener: ProductAdapterOnItemClickListener): RecyclerView.Adapter<SavedProductAdapter.SavedViewHolder>(){

    inner class SavedViewHolder(val binding:SavedItemBinding):RecyclerView.ViewHolder(binding.root)

    val differCallback = object : DiffUtil.ItemCallback<SavedProduct>() {
        override fun areItemsTheSame(oldItem: SavedProduct, newItem: SavedProduct): Boolean {
            return oldItem.productId==newItem.productId
        }

        override fun areContentsTheSame(oldItem: SavedProduct, newItem: SavedProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val binding=SavedItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SavedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val product=differ.currentList[position]
        Glide.with(holder.itemView).load(product.productImage).placeholder(R.drawable.ic_image).into(holder.binding.ivProduct)
        holder.binding.apply {
            this.tvProductName.text=product.productName
        }
        holder.binding.tvAddToCart.setOnClickListener {
            val data=ProductModel(product.productId,product.productName,product.productImage,product.productSellingPrice)
            itemClickListener.addToCart(data,it)
            holder.binding.tvAddToCart.text="In Cart"
        }
        holder.binding.tvRemove.setOnClickListener {
            itemClickListener.deleteSavedProduct(product,it)
        }
        holder.binding.ivProduct.setOnClickListener {
            itemClickListener.viewDetails(product.productId)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}