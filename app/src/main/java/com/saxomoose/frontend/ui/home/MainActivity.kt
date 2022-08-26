package com.saxomoose.frontend.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saxomoose.frontend.R
import com.saxomoose.frontend.services.BackendApi
import com.saxomoose.frontend.services.BackendService

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(R.layout.activity_main), MenuItemSelector {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)

        // BottomNavigation
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavView.setupWithNavController(navController)

        // ActionBar
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_events,
                R.id.fragment_transaction,
                R.id.fragment_overview
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
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
        Log.v(TAG, message)
    }
}