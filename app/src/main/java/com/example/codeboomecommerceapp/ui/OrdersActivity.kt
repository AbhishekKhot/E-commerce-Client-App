package com.example.codeboomecommerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.adapters.OrderHistoryAdapter
import com.example.codeboomecommerceapp.databinding.ActivityOrdersBinding
import com.example.codeboomecommerceapp.model.OrderedProduct
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OrdersActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOrdersBinding
    private val fireStore = Firebase.firestore
    private val list = ArrayList<OrderedProduct>()
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    private var no:String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderHistoryAdapter= OrderHistoryAdapter(this)

        val mSharedPreference = PreferenceManager.getDefaultSharedPreferences(baseContext)
        no = mSharedPreference.getString("PhoneNumber", "")!!

        getOrderHistoryData()
    }


    private fun getOrderHistoryData() {
        fireStore.collection("AllOrders").whereEqualTo("Phone_number",no).get()
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
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }
}