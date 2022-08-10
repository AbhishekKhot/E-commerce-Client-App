package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.adapters.CartProductsAdapter
import com.example.codeboomecommerceapp.adapters.SavedProductAdapter
import com.example.codeboomecommerceapp.databinding.FragmentSavedProductsBinding
import com.example.codeboomecommerceapp.db.ProductDatabase
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.db.SavedProduct
import com.example.codeboomecommerceapp.repository.ProductRepository
import com.example.codeboomecommerceapp.ui.ProductViewModel
import com.example.codeboomecommerceapp.ui.ProductViewModelProviderFactory
import com.example.codeboomecommerceapp.util.ProductAdapterOnItemClickListener
import com.google.android.material.snackbar.Snackbar

class SavedProductsFragment : Fragment(), ProductAdapterOnItemClickListener {

    private var _binding:FragmentSavedProductsBinding?=null
    private val binding get() = _binding!!
    lateinit var viewModel: ProductViewModel
    private lateinit var savedProductAdapter: SavedProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding= FragmentSavedProductsBinding.inflate(layoutInflater)
        val repository= ProductRepository(ProductDatabase.getInstance(requireContext()).productDao())
        val viewModelProviderFactory= ProductViewModelProviderFactory(repository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(ProductViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedProductAdapter= SavedProductAdapter(this)
        setUpRecyclerView()

        viewModel.getAllSavedProduct().observe(viewLifecycleOwner, Observer {
            savedProductAdapter.differ.submitList(it)
        })
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewSavedProduct.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = savedProductAdapter
        }
    }

    override fun viewDetails(id: String) {
        val bundle = Bundle()
        bundle.putString("productID", id)
        findNavController().navigate(R.id.action_savedProductsFragment_to_productDetailsFragment, bundle)
    }

    override fun addToCart(data: ProductModel, view: View) {
        viewModel.insertProduct(data)
        Snackbar.make(view,"Item Added To Cart Successfully",Snackbar.LENGTH_SHORT).show()
    }

    override fun addToWishList(data: SavedProduct, view: View) {
        TODO("Not yet implemented")
    }

    override fun deleteSavedProduct(data: SavedProduct, view: View) {
        viewModel.deleteSavedProduct(data)
        Snackbar.make(view,"Item Removed From You WishList",Toast.LENGTH_SHORT).show()
    }

}