package com.example.codeboomecommerceapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedProducts")
data class SavedProduct(
    @PrimaryKey
    val productId:String,
    @ColumnInfo(name = "product_name")
    val productName:String?="",
    @ColumnInfo(name = "product_image")
    val productImage:String?="",
    @ColumnInfo(name = "product_price")
    val productSellingPrice:String?="",
    @ColumnInfo(name = "product_description")
    val productDescription:String?="",
)
