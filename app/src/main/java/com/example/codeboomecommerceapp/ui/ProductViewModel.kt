package com.example.codeboomecommerceapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.db.SavedProduct
import com.example.codeboomecommerceapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel (
    val repository: ProductRepository,
) : ViewModel() {

    fun insertProduct(product: ProductModel) = viewModelScope.launch {
        repository.insertProduct(product)
    }

    fun insertSavedProduct(product: SavedProduct) = viewModelScope.launch {
        repository.insertSavedProduct(product)
    }

    fun deleteProduct(product: ProductModel) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    fun deleteSavedProduct(product:SavedProduct) = viewModelScope.launch {
        repository.deleteSavedProduct(product)
    }

    fun getAllProducts() = repository.getAllProduct()

    fun getAllSavedProduct() = repository.getAllSavedProduct()

    fun isExits(id:String)=repository.isExits(id)

}