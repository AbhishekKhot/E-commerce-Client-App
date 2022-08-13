package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.FragmentAccountBinding
import com.example.codeboomecommerceapp.model.UserInfo
import com.google.firebase.firestore.FirebaseFirestore

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private  val fireStore = FirebaseFirestore.getInstance()
    private lateinit var number:String
    private lateinit var name:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserName()

        binding.ivEdit.setOnClickListener {
            binding.inputLayout.isEnabled=true
            if(name!=binding.etUserName.text.toString()){
                editUserName()
            }
        }

        binding.cardMyOrder.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_cartFragment)
        }

        binding.cardOrderHistory.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_ordersActivity)
        }

        binding.cardSettings.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_settingsFragment)
        }

        binding.cardTermsAndConditions.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("url","https://www.termsandcondiitionssample.com/live.php?token=HNIN3gDMoYlq6AK5nidELQqsVOH7swbR")
            findNavController().navigate(R.id.action_accountFragment_to_termsAndConditionsFragment,bundle)
        }
    }

    private fun editUserName() {
        val user_data= hashMapOf<String,Any>()
        user_data["user_name"]=binding.etUserName.text.toString()

        fireStore.collection("Users").document(number).update(user_data)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(requireActivity(),"Successfully Updated Your Name",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(),"Something Went Wrong, Please Try Again",Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserName() {
        fireStore.collection("Users").get()
            .addOnCompleteListener {
                val data= it.result.toObjects(UserInfo::class.java)
                if(data.isEmpty()){
                    Toast.makeText(requireContext(),"Enter Details Please",Toast.LENGTH_SHORT).show()
                }else{
                    binding.etUserName.setText(data[0].user_name.toString())
                    number=data[0].user_number.toString()
                    name=data[0].user_name.toString()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Enter Details Please",Toast.LENGTH_SHORT).show()
            }
    }

}