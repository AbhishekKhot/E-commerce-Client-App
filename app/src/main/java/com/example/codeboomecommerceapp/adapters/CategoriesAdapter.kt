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
import com.example.codeboomecommerceapp.databinding.CategoryItemBinding
import com.example.codeboomecommerceapp.model.Category
import com.example.codeboomecommerceapp.util.CategoriesItemClickListener

class CategoriesAdapter(val itemClickListener: CategoriesItemClickListener) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    val diffCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.category_name==newItem.category_name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.binding.tvCategory.text = category.category_name
        Glide.with(holder.itemView).load(category.category_image).placeholder(R.drawable.ic_image)
            .into(holder.binding.ivCategory)
        holder.itemView.setOnClickListener {
            itemClickListener.GoToCategoryProduct(category.category_name.toString())
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}