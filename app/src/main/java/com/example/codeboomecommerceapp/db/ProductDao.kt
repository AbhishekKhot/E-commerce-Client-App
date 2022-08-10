package com.example.codeboomecommerceapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.codeboomecommerceapp.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductModel)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSavedProduct(product: ProductModel)

    @Delete
    suspend fun deleteProduct(product: ProductModel)

//    @Delete
//    suspend fun deleteSavedProduct(product: ProductModel)

    @Query("SELECT * FROM products")
    fun getAllProduct(): LiveData<List<ProductModel>>

    @Query("SELECT * FROM Products WHERE productId=:id")
    fun isExits(id:String):ProductModel
}