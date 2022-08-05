package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.FragmentHomeBinding
import com.example.codeboomecommerceapp.adapters.CategoriesAdapter
import com.example.codeboomecommerceapp.adapters.ProductAdapter
import com.example.codeboomecommerceapp.model.Category
import com.example.codeboomecommerceapp.model.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val fireStore = Firebase.firestore
    private val productAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSlider()
        getCategories()
        getProducts()



    }



    private fun getProducts() {
        val list = ArrayList<Product>()
        fireStore.collection("Products").get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(Product::class.java)
                    list.add(data!!)
                }
                productAdapter.differ.submitList(list)
                binding.recyclerViewAllProducts.adapter = productAdapter
            }
    }

    private fun setUpSlider() {
        val list = listOf<CarouselItem>(
            CarouselItem(R.drawable.i1),
            CarouselItem(R.drawable.i2),
        )
        binding.carousel.addData(list)
    }

    private fun getCategories() {
        val list = ArrayList<Category>()
        fireStore.collection("Categories").get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(Category::class.java)
                    list.add(data!!)
                }
                val categoryAdapter = CategoriesAdapter()
                categoryAdapter.differ.submitList(list)
                binding.recyclerViewCategories.adapter = categoryAdapter
            }
    }

}