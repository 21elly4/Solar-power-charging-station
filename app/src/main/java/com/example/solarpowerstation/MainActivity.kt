package com.example.solarpowerstation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.solarpowerstation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Safely get NavHostFragment and NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        if (navHostFragment != null) {
            val navController = navHostFragment.navController

            // Set up ActionBar for navigation (if using one)
            setupActionBarWithNavController(navController)

            // Set up BottomNavigationView with NavController
            binding.bottomNavigation.setupWithNavController(navController)
        } else {
            // Handle the error or log it to identify the issue
            Log.e("MainActivity", "NavHostFragment not found")
        }
    }

    // Handle the navigation when up button is pressed
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
