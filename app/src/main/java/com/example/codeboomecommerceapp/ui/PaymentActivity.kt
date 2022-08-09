package com.example.codeboomecommerceapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.codeboomecommerceapp.databinding.ActivityPaymentBinding
import com.example.codeboomecommerceapp.db.ProductDatabase
import com.example.codeboomecommerceapp.db.ProductModel
import com.example.codeboomecommerceapp.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityPaymentBinding
    private lateinit var viewModel: ProductViewModel
    private var dataList = mutableListOf<ProductModel>()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var totalAmount: String
    private lateinit var phoneNumber: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = ProductRepository(ProductDatabase.getInstance(this).productDao())
        val viewModelProviderFactory = ProductViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(ProductViewModel::class.java)


        viewModel.getAllProducts().observe(this, Observer {
            dataList = it as MutableList<ProductModel>
        })

        totalAmount = intent.getStringExtra("totalAmount").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()

        Checkout.preload(applicationContext)

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_kTZdAKmkK84yzH")


        try {
            val options = JSONObject()
            options.put("name", "CodeBoom E-commerce App")
            options.put("description", "Order Charges")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", totalAmount.toInt()*100)
            options.put("prefill.email", "codeboomhelp@gmai.com")
            options.put("prefill.contact", "9988776655")
            checkout.open(this, options)
        }
        catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Successfully payed", Toast.LENGTH_SHORT).show()

        uploadOrderDataToFireStore()
    }

    private fun uploadOrderDataToFireStore() {
        dataList.forEach {
            getProductData(it.productId)
        }
    }

    private fun getProductData(productId: String) {
        firebaseFirestore.collection("Products").document(productId).get()
            .addOnSuccessListener {
                saveDataToFireStore(
                    productId,
                    it.getString("product_name"),
                    it.getString("product_cover_image"),
                    it.getString("product_selling_price"),
                )
            }
            .addOnFailureListener {

            }
    }

    @SuppressLint("SimpleDateFormat")
    private fun saveDataToFireStore(
        productId: String,
        name: String?,
        image: String?,
        price: String?,
    ) {

        val fireStore=Firebase.firestore.collection("AllOrders")
        val key =  fireStore.document().id

        val order_data = hashMapOf<String, Any>()
        order_data["Product_Name"] = name!!
        order_data["Product_Image"] = image!!
        order_data["Product_Price"] = price!!
        order_data["Product_Id"] = productId
        order_data["Phone_number"]=phoneNumber
        order_data["Order_Id"] = key

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        order_data["Order_time"] = currentDate
        order_data["Order_status"]="Order Placed"

        fireStore.add(order_data)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully Added Information", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something Went Wrong, Please Try Again", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Error in payment", Toast.LENGTH_SHORT).show()
    }


}