package com.saxomoose.frontend.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saxomoose.frontend.R
import com.saxomoose.frontend.databinding.ActivityMainBinding
import com.saxomoose.frontend.services.BackendApi
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_events, R.id.navigation_transaction, R.id.navigation_overview))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        lifecycleScope.launch {
            val response = BackendApi.retrofitService.getEventCategories(1)
            logging(response.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // This code will ask the navController to handle navigating up in the app. Otherwise, fall back to back to the superclass implementation (in  appCompatActivity) of handling the Up button.
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

fun logging(message: String) {
    Log.v(TAG, message)
}