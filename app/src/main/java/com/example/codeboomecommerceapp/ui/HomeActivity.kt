package com.example.codeboomecommerceapp.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.codeboomecommerceapp.R
import com.example.codeboomecommerceapp.adapters.ProductAdapter
import com.example.codeboomecommerceapp.databinding.ActivityHomeBinding
import com.example.codeboomecommerceapp.db.ProductDatabase
import com.example.codeboomecommerceapp.repository.ProductRepository
import com.example.codeboomecommerceapp.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
     lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CodeBoomEcommerceApp)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val repository=ProductRepository(ProductDatabase.getInstance(this).productDao())
        val viewModelProviderFactory=ProductViewModelProviderFactory(repository)
        viewModel=ViewModelProvider(this,viewModelProviderFactory).get(ProductViewModel::class.java)




        val navView:BottomNavigationView=binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration= AppBarConfiguration(setOf(
            R.id.homeFragment,R.id.cartFragment,R.id.accountFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}