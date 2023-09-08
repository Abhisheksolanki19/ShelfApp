package com.abhishek.shelfapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.abhishek.shelfapp.R
import com.abhishek.shelfapp.databinding.ActivityMainBinding
import com.abhishek.shelfapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val isLoggedIn = viewModel.getIsUserLoggedIn()

        // Determine the starting destination based on the login status
        val startDestination = if (isLoggedIn) {
            R.id.homeFragment // Destination ID for HomeFragment
        } else {
            R.id.signUpFragment // Destination ID for SignUpFragment
        }
        // Set the starting destination in the NavController
        navController.navigate(startDestination)

    }
}