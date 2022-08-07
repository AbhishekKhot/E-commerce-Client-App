package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.codeboomecommerceapp.databinding.FragmentUserDetailsBinding
import com.example.codeboomecommerceapp.model.UserInfo
import com.google.firebase.firestore.FirebaseFirestore

class UserDetailsFragment : Fragment() {

    private var _binding:FragmentUserDetailsBinding?=null
    private val binding get() = _binding!!
    private val fireStore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding= FragmentUserDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    }

    private fun loadUserData() {
        fireStore.collection("Users").get()
            .addOnCompleteListener {
                val data= it.result.toObjects(UserInfo::class.java)
                if(data.isEmpty()){
                    Toast.makeText(requireContext(),"Enter Details Please",Toast.LENGTH_SHORT).show()
                }else{
                    binding.etName.setText(data[0].user_name.toString())
                    binding.etNumber.setText(data[0].user_number.toString())
                    binding.etCity.setText(data[0].user_city.toString())
                    binding.etState.setText(data[0].user_state.toString())
                    binding.etPinCode.setText(data[0].user_pin.toString())
                    binding.etAddress.setText(data[0].user_address.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Enter Details Please",Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeUserDataToFireStore() {
        val user_data= hashMapOf<String,Any>()
        user_data["user_number"]=binding.etNumber.text.toString()
        user_data["user_name"]=binding.etName.text.toString()
        user_data["user_city"]=binding.etCity.text.toString()
        user_data["user_state"]=binding.etState.text.toString()
        user_data["user_pin"]=binding.etPinCode.text.toString()
        user_data["user_address"]=binding.etAddress.text.toString()

        fireStore.collection("Users").document(binding.etNumber.text.toString()).set(user_data)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(requireActivity(),"Successfully Added Information",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(),"Something Went Wrong, Please Try Again",Toast.LENGTH_SHORT).show()
            }
    }
}