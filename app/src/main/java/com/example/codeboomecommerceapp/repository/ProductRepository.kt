package com.example.codeboomecommerceapp.repository

import com.example.codeboomecommerceapp.db.ProductDao
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.db.SavedProduct

class ProductRepository(
    val db:ProductDao
) {
    suspend fun insertProduct(product:ProductModel) =
        db.insertProduct(product)

    suspend fun insertSavedProduct(product:SavedProduct)=
        db.insertSavedProduct(product)

    suspend fun deleteProduct(product:ProductModel) =
        db.deleteProduct(product)

    suspend fun deleteSavedProduct(product:SavedProduct)=
        db.deleteSavedProduct(product)

    fun getAllProduct() = db.getAllProduct()

    fun getAllSavedProduct() = db.getAllSavedProduct()

    fun isExits(id:String) = db.isExits(id)

}