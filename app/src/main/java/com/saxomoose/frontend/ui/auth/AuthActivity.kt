package com.saxomoose.frontend.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.saxomoose.frontend.R
import com.saxomoose.frontend.ui.auth.login.ActivityLauncher
import com.saxomoose.frontend.ui.home.MainActivity

class AuthActivity : AppCompatActivity(R.layout.activity_auth), ActivityLauncher {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // sharedPref.edit().clear().apply()

        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val token = sharedPref.getString(getString(R.string.token), null)
        val userId : Int = sharedPref.getInt(getString(R.string.userId), -1)
        // token = "1|cYpZHYCdcL5HDY0LsVd1PriWMTZwSkhjeeoffEhY"
        if (token != null && userId != -1) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(MainActivity.USER_ID, userId)
            startActivity(intent)
            finish()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun launchMainActivity(userId: Int) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(MainActivity.USER_ID, userId)
        startActivity(intent)
        finish()
    }
}