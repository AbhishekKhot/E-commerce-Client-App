package com.example.codeboomecommerceapp.repository

import com.example.codeboomecommerceapp.db.ProductDao
import com.example.codeboomecommerceapp.db.ProductModel

class ProductRepository(
    val db:ProductDao
) {
    suspend fun insertProduct(product:ProductModel) =
        db.insertProduct(product)

//    suspend fun insertSavedProduct(product: ProductModel)=
//        db.insertSavedProduct(product)

    suspend fun deleteProduct(product:ProductModel) =
        db.deleteProduct(product)

//    suspend fun deleteSavedProduct(product:ProductModel) =
//        db.deleteSavedProduct(product)

    fun getAllProduct() = db.getAllProduct()

    fun isExits(id:String) = db.isExits(id)

}