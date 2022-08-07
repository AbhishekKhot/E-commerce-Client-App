package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.FragmentCategoriesBinding
import com.example.codeboomecommerceapp.adapters.CategoriesProductAdapter
import com.example.codeboomecommerceapp.model.Product
import com.example.codeboomecommerceapp.util.GridSpacingItemDecoration
import com.example.codeboomecommerceapp.util.ProductAdapterOnItemClickListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoriesFragment : Fragment(), ProductAdapterOnItemClickListener {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val firebase = Firebase.firestore
    private val args: CategoriesFragmentArgs by navArgs()
    private val categoriesProductAdapter = CategoriesProductAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrangeItemsRecyclerView()

        val list = ArrayList<Product>()
        firebase.collection("Products").whereEqualTo("product_category", args.categoryName)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(Product::class.java)
                    list.add(data!!)
                }
                categoriesProductAdapter.differ.submitList(list)
                binding.recyclerViewCategoriesProduct.adapter = categoriesProductAdapter
            }
    }

    private fun arrangeItemsRecyclerView() {
        binding.recyclerViewCategoriesProduct.apply {
            val spanCount = 3
            val spacing = 50
            val includeEdge = false
            this.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
    }

    override fun viewDetails(id: String) {
        val bundle = Bundle()
        bundle.putString("productID", id)
        findNavController().navigate(R.id.action_categoriesFragment_to_productDetailsFragment, bundle)
    }

    override fun addToCart(id: String) {
        TODO("Not yet implemented")
    }
}