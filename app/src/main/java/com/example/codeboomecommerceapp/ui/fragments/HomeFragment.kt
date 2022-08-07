package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.FragmentHomeBinding
import com.example.codeboomecommerceapp.adapters.CategoriesAdapter
import com.example.codeboomecommerceapp.adapters.ProductAdapter
import com.example.codeboomecommerceapp.model.Category
import com.example.codeboomecommerceapp.model.Product
import com.example.codeboomecommerceapp.util.CategoriesItemClickListener
import com.example.codeboomecommerceapp.util.ProductAdapterOnItemClickListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragment : Fragment(), ProductAdapterOnItemClickListener, CategoriesItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val fireStore = Firebase.firestore
    private val productAdapter = ProductAdapter(this)
    private val categoryAdapter = CategoriesAdapter(this)
    val list = ArrayList<Product>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
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
                categoryAdapter.differ.submitList(list)
                binding.recyclerViewCategories.adapter = categoryAdapter
            }
    }

    override fun viewDetails(id: String) {
            val bundle = Bundle()
            bundle.putString("productID", id)
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment, bundle)
    }

    override fun addToCart(id: String) {
        TODO("Not yet implemented")
    }

    override fun GoToCategoryProduct(name: String) {
        val bundle = Bundle()
        bundle.putString("categoryName", name)
        findNavController().navigate(R.id.action_homeFragment_to_categoriesFragment,bundle)
    }

}