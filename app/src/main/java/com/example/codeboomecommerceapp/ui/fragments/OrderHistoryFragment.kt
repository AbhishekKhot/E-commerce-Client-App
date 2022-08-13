package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.codeboomecommerceapp.adapters.OrderHistoryAdapter
import com.example.codeboomecommerceapp.databinding.FragmentOrderHistoryBinding
import com.example.codeboomecommerceapp.model.OrderedProduct
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OrderHistoryFragment : Fragment() {

    private var _binding: FragmentOrderHistoryBinding?=null
    private val binding get() = _binding!!
    private val fireStore = Firebase.firestore
    private val list = ArrayList<OrderedProduct>()
    private lateinit var orderHistoryAdapter:OrderHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOrderHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderHistoryAdapter= OrderHistoryAdapter(requireActivity())
        getOrderHistoryData()
    }

    private fun getOrderHistoryData() {

        fireStore.collection("AllOrders").get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(OrderedProduct::class.java)
                    list.add(data!!)
                }
                orderHistoryAdapter.differ.submitList(list)
                binding.recyclerViewOrderHistory.adapter=orderHistoryAdapter

            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

}