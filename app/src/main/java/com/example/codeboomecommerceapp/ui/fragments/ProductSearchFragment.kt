package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.adapters.ProductSearchAdapter
import com.example.codeboomecommerceapp.databinding.FragmentProductSearchBinding
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.db.SavedProduct
import com.example.codeboomecommerceapp.model.Product
import com.example.codeboomecommerceapp.util.ProductAdapterOnItemClickListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class ProductSearchFragment : Fragment(), ProductAdapterOnItemClickListener {
    private var _binding: FragmentProductSearchBinding? = null
    private val binding get() = _binding!!
    private val fireStore = Firebase.firestore
    private val searchAdapter = ProductSearchAdapter(this)
    private val list = ArrayList<Product>()
    private val SearchList = ArrayList<Product>()
    private val args: ProductSearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProducts()
    }

    private fun getProducts() {
        fireStore.collection("Products").get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(Product::class.java)
                    list.add(data!!)
                }

                for(item in list){
                    if(item.product_description!!.toLowerCase().contains(args.searchQuery.toLowerCase())){
                        SearchList.add(item)
                    }
                }

                if(SearchList.size>0){
                    searchAdapter.differ.submitList(SearchList)
                }
                else{
                    searchAdapter.differ.submitList(list)
                }
                binding.recyclerViewSearch.adapter = searchAdapter
            }
    }

    override fun viewDetails(id: String) {
       val bundle=Bundle()
        bundle.putString("productID",id)
        findNavController().navigate(R.id.action_productSearchFragment_to_productDetailsFragment,bundle)
    }

    override fun addToCart(data: ProductModel, view: View) {
        TODO("Not yet implemented")
    }

    override fun addToWishList(data: SavedProduct, view: View) {
        TODO("Not yet implemented")
    }

    override fun deleteSavedProduct(data: SavedProduct, view: View) {
        TODO("Not yet implemented")
    }

}