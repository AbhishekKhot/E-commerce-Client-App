package com.example.codeboomecommerceapp.ui.fragments

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.FragmentProductDetailsBinding
import com.example.codeboomecommerceapp.db.ProductDao
import com.example.codeboomecommerceapp.db.ProductDatabase
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.ui.HomeActivity
import com.example.codeboomecommerceapp.ui.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailsFragmentArgs by navArgs()
    private val firebase = Firebase.firestore
    lateinit var viewModel : ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        viewModel = (activity as HomeActivity).viewModel
        getProductDetails()
    }

    private fun getProductDetails() {
        firebase.collection("Products").document(args.productID).get()
            .addOnSuccessListener {
                val list = it.get("product_images") as ArrayList<String>
                val name = it.getString("product_name").toString()
                val price = it.getString("product_selling_price").toString()
                val image = it.getString("product_cover_image").toString()
                val id = it.getString("product_id").toString()
                val mrp = it.getString("product_mrp").toString()

                binding.tvProductName.text = it.getString("product_name")
                binding.tvProductDescription.text = it.getString("product_description")
                binding.tvProductPrice.text = "$" + it.getString("product_mrp")
                binding.tvProductSellingPrice.text = "$" + it.getString("product_selling_price")
                val discount=mrp.toInt()-price.toInt()
                val rateOfDiscount=(100*discount)/mrp.toInt()
                binding.tvDiscount.text= "${rateOfDiscount}% Off"

                val slideImageList = ArrayList<SlideModel>()
                for (data in list) {
                    slideImageList.add(SlideModel(data, ScaleTypes.CENTER_INSIDE))
                }

                addToCart(id, name, image, price)
                binding.ivSlider.setImageList(slideImageList)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),
                    "Something went wrong ${it.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    private fun addToCart(id: String, name: String, image: String, price: String) {
        if (viewModel.isExits(id) != null) {
            goToCart()
        }

        binding.btnAddToCart.setOnClickListener {
            val data = ProductModel(id, name, image, price)
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.insertProduct(data)
            }
            goToCart()
            Snackbar.make(it, "Item Added To Cart Successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun goToCart() {
        binding.btnAddToCart.visibility = View.INVISIBLE
        binding.btnGoToCart.visibility = View.VISIBLE
        binding.btnGoToCart.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsFragment_to_cartFragment)
        }
    }
}