package com.example.codeboomecommerceapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.OrderHistoryBinding
import com.example.codeboomecommerceapp.model.OrderedProduct
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class OrderHistoryAdapter(val context: Context) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

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
        if(product.Order_status=="Order Cancelled"){
            holder.binding.tvCancel.visibility=View.INVISIBLE
        }

        holder.binding.tvCancel.setOnClickListener {
            val alert = AlertDialog.Builder(context)
            alert.setTitle("CANCEL ORDER")
                .setMessage("DO YOU WANT TO CANCEL THE ORDER ?")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES") { dialog, which ->
                    val fireStore= Firebase.firestore

                    fireStore.collection("AllOrders").whereEqualTo("Product_Id",product.Product_Id).get()
                        .addOnCompleteListener {
                            for (snapshot in it.result) {
                                fireStore.collection("AllOrders")
                                    .document(snapshot.id).update("Order_status","Order Cancelled")
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context,"Failed, Please Try Again",Toast.LENGTH_SHORT).show()
                        }
                    notifyDataSetChanged()
                    holder.binding.tvCancel.text="Order Cancelled"
                }
            alert.show()
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}