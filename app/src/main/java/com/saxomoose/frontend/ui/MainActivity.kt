package com.saxomoose.frontend.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saxomoose.frontend.R
import com.saxomoose.frontend.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main), MenuItemSelector {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // BottomNavigation
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavView.setupWithNavController(navController)

        // ActionBar
        appBarConfiguration = AppBarConfiguration(setOf(R.id.fragment_events, R.id.fragment_transaction, R.id.fragment_overview))
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Backend connectivity test
//        lifecycleScope.launch {
//            val response = BackendApi.retrofitService.getEventCategories(1)
//            logging(response.toString())
//            val response2 = BackendApi.retrofitService.getUserEvents(1)
//            logging(response2.toString())
//        }
    }

    override fun selectEventsMenuItem() {
        val bottomNavView: BottomNavigationView? = findViewById(R.id.bottom_nav_view)
        bottomNavView?.selectedItemId = R.id.fragment_events
        bottomNavView?.menu?.findItem(R.id.fragment_events)?.isChecked = true
    }

    override fun onSupportNavigateUp(): Boolean {
        // This code will ask the navController to handle navigating up in the app. Otherwise, fall back to back to the superclass implementation (in  appCompatActivity) of handling the Up button.
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logging(message: String) {
        Log.v("MainActivity", message)
    }
}