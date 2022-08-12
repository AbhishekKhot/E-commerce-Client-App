package com.example.codeboomecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.OrderHistoryBinding
import com.example.codeboomecommerceapp.model.OrderedProduct

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    inner class OrderHistoryViewHolder(val binding: OrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    val differCallBack = object : DiffUtil.ItemCallback<OrderedProduct>() {
        override fun areItemsTheSame(oldItem: OrderedProduct, newItem: OrderedProduct): Boolean {
            return oldItem.Product_Id == newItem.Product_Id
        }

        override fun areContentsTheSame(oldItem: OrderedProduct, newItem: OrderedProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val binding =
            OrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val product = differ.currentList[position]
        Glide.with(holder.itemView).load(product.Product_Image).placeholder(R.drawable.ic_image)
            .into(holder.binding.ivProduct)
        holder.binding.apply {
            this.tvName.text = product.Product_Name
            this.tvPrice.text = "$"+product.Product_Price
            this.tvDate.text = product.Order_time
            this.tvStatus.text=product.Order_status
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}