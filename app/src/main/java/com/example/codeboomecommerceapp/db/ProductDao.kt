package com.example.codeboomecommerceapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.codeboomecommerceapp.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductModel)

    @Delete
    suspend fun deleteProduct(product: ProductModel)

    @Query("SELECT * FROM products")
    fun getAllProduct(): LiveData<List<ProductModel>>

    @Query("SELECT * FROM Products WHERE productId=:id")
    fun isExits(id:String):ProductModel


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedProduct(product: SavedProduct)

    @Delete
    suspend fun deleteSavedProduct(product: SavedProduct)

    @Query("SELECT * FROM savedproducts")
    fun getAllSavedProduct(): LiveData<List<SavedProduct>>
}