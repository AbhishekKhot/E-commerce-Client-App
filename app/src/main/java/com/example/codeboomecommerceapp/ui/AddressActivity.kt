package com.example.codeboomecommerceapp.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.codeboomecommerceapp.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddressActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddressBinding
    private val firebaseFirestore = Firebase.firestore
    private val args: AddressActivityArgs by navArgs()
    private var no:String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mSharedPreference = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val value = mSharedPreference.getString("PhoneNumber", "")
        no = mSharedPreference.getString("PhoneNumber", "")!!

        binding.etName.setText(value)
        loadUserData()


        binding.btnProceed.setOnClickListener {
            if(binding.etName.text.toString().isEmpty()){
                binding.etName.requestFocus()
                binding.etName.error="Empty"
            }
            else if(binding.etNumber.text.toString().isEmpty()){
                binding.etNumber.requestFocus()
                binding.etNumber.error="Empty"
            }
            else if(binding.etCity.text.toString().isEmpty()){
                binding.etCity.requestFocus()
                binding.etCity.error="Empty"
            }
            else if(binding.etState.text.toString().isEmpty()){
                binding.etState.requestFocus()
                binding.etState.error="Empty"
            }
            else if(binding.etPinCode.text.toString().isEmpty()){
                binding.etPinCode.requestFocus()
                binding.etPinCode.error="Empty"
            }
            else if(binding.etAddress.text.toString().isEmpty()){
                binding.etAddress.requestFocus()
                binding.etPinCode.error="Empty"
            }
            else{
                storeUserDataToFireStore()
            }
        }

//        binding.btnProceed.setOnClickListener {
//            val intent=Intent(this,PaymentActivity::class.java)
//            intent.putExtra("totalAmount",args.totalAmount)
//            intent.putExtra("phoneNumber",binding.etNumber.text.toString())
//            startActivity(intent)
//        }

    }

    private fun storeUserDataToFireStore() {
        val user_data= hashMapOf<String,Any>()
        user_data["user_number"]=binding.etNumber.text.toString()
        user_data["user_name"]=binding.etName.text.toString()
        user_data["user_city"]=binding.etCity.text.toString()
        user_data["user_state"]=binding.etState.text.toString()
        user_data["user_pin"]=binding.etPinCode.text.toString()
        user_data["user_address"]=binding.etAddress.text.toString()

        firebaseFirestore.collection("Users").document(binding.etNumber.text.toString()).set(user_data)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val intent=Intent(this,PaymentActivity::class.java)
                    intent.putExtra("totalAmount",args.totalAmount)
                    intent.putExtra("phoneNumber",no)
                    startActivity(intent)
                    Toast.makeText(this,"Successfully Added Information",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"Something Went Wrong, Please Try Again",Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserData() {
        firebaseFirestore.collection("Users").document(no).get()
            .addOnSuccessListener {
                val name = it.get("user_name").toString()
                val number = it.getString("user_number").toString()
                val city = it.getString("user_city").toString()
                val state = it.getString("user_state").toString()
                val pin = it.getString("user_pin").toString()
                val address = it.getString("user_address").toString()

                if(it.data?.isEmpty() == true){
                    Toast.makeText(this,"Please provide address details",Toast.LENGTH_SHORT).show()
                }
                else{
                    binding.etName.setText(name)
                    binding.etNumber.setText(no)
                    binding.etCity.setText(city)
                    binding.etState.setText(state)
                    binding.etPinCode.setText(pin)
                    binding.etAddress.setText(address)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"Please provide address details",Toast.LENGTH_SHORT).show()
            }
    }
}