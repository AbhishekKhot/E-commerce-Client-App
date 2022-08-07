package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.adapters.CartProductsAdapter
import com.example.codeboomecommerceapp.databinding.FragmentCartBinding
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.ui.HomeActivity
import com.example.codeboomecommerceapp.ui.ProductViewModel
import com.example.codeboomecommerceapp.util.CartAdapterOnItemClickListener
import com.google.android.material.snackbar.Snackbar


class CartFragment : Fragment(),CartAdapterOnItemClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartProductsAdapter
    private lateinit var viewModel:ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartAdapter = CartProductsAdapter(this)
        setUpRecyclerView()

        viewModel = (activity as HomeActivity).viewModel

        viewModel.getAllProducts().observe(viewLifecycleOwner, Observer {
            cartAdapter.differ.submitList(it)

            calculateCostAndAmount(view,it)
        })
    }

    private fun calculateCostAndAmount(view: View,list: List<ProductModel>?) {
         var totalAmount=0
        for(item in list!!){
            totalAmount+=item.productSellingPrice!!.toInt()
        }

        binding.tvAmount.text="$" + totalAmount.toString()

        binding.btnCheckOut.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("totalAmount",totalAmount.toString())
            findNavController().navigate(R.id.action_cartFragment_to_userDetailsFragment)
        }
    }


    private fun setUpRecyclerView() {
        binding.recyclerViewCart.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = cartAdapter
        }
    }

    override fun deleteItem(data: ProductModel,view:View) {
        viewModel.deleteProduct(data)
        Snackbar.make(view,"Item Removed Successfully From Cart",Snackbar.LENGTH_SHORT).show()
    }

}