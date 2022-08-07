package com.example.codeboomecommerceapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.databinding.FragmentTermsAndConditionsBinding

class TermsAndConditionsFragment : Fragment() {
    private var _binding:FragmentTermsAndConditionsBinding?=null
    private val binding get() = _binding!!
    private val args:TermsAndConditionsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding= FragmentTermsAndConditionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = args.url

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url!!)
    }
}