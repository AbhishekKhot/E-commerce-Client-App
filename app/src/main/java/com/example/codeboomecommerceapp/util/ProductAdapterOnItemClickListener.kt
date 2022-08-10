package com.example.codeboomecommerceapp.util

import android.view.View
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.db.SavedProduct

interface ProductAdapterOnItemClickListener {
    fun viewDetails(id: String)
    fun addToCart(data:ProductModel,view: View)
    fun addToWishList(data:SavedProduct,view: View)
    fun deleteSavedProduct(data: SavedProduct,view: View)
}