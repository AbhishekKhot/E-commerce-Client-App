package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.adapters.CartProductsAdapter
import com.example.codeboomecommerceapp.databinding.FragmentCartBinding
import com.example.codeboomecommerceapp.ui.HomeActivity
import com.example.codeboomecommerceapp.ui.ProductViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartProductsAdapter
    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel
        cartAdapter = CartProductsAdapter(viewModel)
        setUpRecyclerView()

        viewModel.getAllProducts().observe(viewLifecycleOwner, Observer {
            cartAdapter.differ.submitList(it)
        })

    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCart.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = cartAdapter
        }
    }

}