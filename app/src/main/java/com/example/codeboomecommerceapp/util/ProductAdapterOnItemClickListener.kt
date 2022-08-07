package com.example.codeboomecommerceapp.util

import android.view.View
import com.example.codeboomecommerceapp.db.ProductModel

interface ProductAdapterOnItemClickListener {
    fun viewDetails(id: String)
    fun addToCart(data:ProductModel,view: View)
}