package com.example.codeboomecommerceapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.adapters.CategoriesAdapter
import com.example.codeboomecommerceapp.adapters.ProductAdapter
import com.example.codeboomecommerceapp.databinding.FragmentHomeBinding
import com.example.codeboomecommerceapp.db.ProductDatabase
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.model.Category
import com.example.codeboomecommerceapp.model.Product
import com.example.codeboomecommerceapp.repository.ProductRepository
import com.example.codeboomecommerceapp.ui.ProductViewModel
import com.example.codeboomecommerceapp.ui.ProductViewModelProviderFactory
import com.example.codeboomecommerceapp.util.CategoriesItemClickListener
import com.example.codeboomecommerceapp.util.ProductAdapterOnItemClickListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mancj.materialsearchbar.MaterialSearchBar.OnSearchActionListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class HomeFragment : Fragment(), ProductAdapterOnItemClickListener, CategoriesItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val fireStore = Firebase.firestore
    private val productAdapter = ProductAdapter(this)
    private val categoryAdapter = CategoriesAdapter(this)
    val list = ArrayList<Product>()
    lateinit var viewModel:ProductViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        val repository= ProductRepository(ProductDatabase.getInstance(requireContext()).productDao())
        val viewModelProviderFactory= ProductViewModelProviderFactory(repository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(ProductViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSlider()
        getCategories()
        getProducts()

        binding.searchBar.setOnSearchActionListener(object : OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}
            override fun onSearchConfirmed(text: CharSequence) {
                val bundle=Bundle()
                bundle.putString("searchQuery",text.toString())
                findNavController().navigate(R.id.action_homeFragment_to_productSearchFragment,bundle)
            }

            override fun onButtonClicked(buttonCode: Int) {}
        })
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

    override fun addToCart(data: ProductModel, view: View) {
        viewModel.insertProduct(data)
        Snackbar.make(view,"Item added to cart",Snackbar.LENGTH_SHORT).show()
    }


    override fun GoToCategoryProduct(name: String) {
        val bundle = Bundle()
        bundle.putString("categoryName", name)
        findNavController().navigate(R.id.action_homeFragment_to_categoriesFragment, bundle)
    }

}