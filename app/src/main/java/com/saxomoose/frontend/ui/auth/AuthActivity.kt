package com.saxomoose.frontend.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.saxomoose.frontend.FrontEndApplication
import com.saxomoose.frontend.R
import com.saxomoose.frontend.services.BackendApi
import com.saxomoose.frontend.ui.auth.login.ActivityLauncher
import com.saxomoose.frontend.ui.home.MainActivity

class AuthActivity : AppCompatActivity(R.layout.activity_auth), ActivityLauncher {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If token and userId are set, start MainActivity.
        val sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val token = sharedPref.getString(getString(R.string.token), null)
        val userId: Int = sharedPref.getInt(getString(R.string.userId), -1)
        if (token != null && userId != -1) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        title = "Authentication"
    }

    override fun launchMainActivity() {
        // Resets the backend service. A new singleton object is created in MainActivity with the user token.
        BackendApi.deallocateInstance()
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}