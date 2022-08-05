package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardMyOrder.setOnClickListener {

        }

        binding.cardOrderHistory.setOnClickListener {
            binding.cardYourOrder.setOnClickListener {

            }

            binding.cardSettings.setOnClickListener {
                findNavController().navigate(R.id.action_accountFragment_to_settingsFragment)
            }

            binding.cardTermsAndConditions.setOnClickListener {
                findNavController().navigate(R.id.action_accountFragment_to_termsAndConditionsFragment)
            }

            binding.cardYourOrder.setOnClickListener {
                findNavController().navigate(R.id.action_accountFragment_to_orderHistoryFragment)
            }
        }
    }
}