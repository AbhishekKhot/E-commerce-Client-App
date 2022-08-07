package com.example.codeboomecommerceapp.util

interface ProductAdapterOnItemClickListener {
    fun viewDetails(id: String)
    fun addToCart(id:String)
}