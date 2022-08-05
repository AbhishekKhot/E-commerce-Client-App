package com.example.codeboomecommerceapp.repository

import com.example.codeboomecommerceapp.db.ProductDao
import com.example.codeboomecommerceapp.db.ProductDatabase
import com.example.codeboomecommerceapp.db.ProductModel

class ProductRepository(
    val db:ProductDao
) {
    suspend fun insertProduct(product:ProductModel) =
        db.insertProduct(product)

    suspend fun deleteProduct(product:ProductModel) =
        db.deleteProduct(product)

    fun getAllProduct() = db.getAllProduct()

    fun isExits(id:String) = db.isExits(id)

}