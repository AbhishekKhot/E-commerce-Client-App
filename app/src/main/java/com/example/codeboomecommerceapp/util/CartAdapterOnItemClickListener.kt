package com.example.codeboomecommerceapp.util

import android.view.View
import com.example.codeboomecommerceapp.db.ProductModel

interface CartAdapterOnItemClickListener {
    fun deleteItem(data:ProductModel,view: View)
}