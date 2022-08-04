package com.saxomoose.frontend.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.saxomoose.frontend.R
import com.saxomoose.frontend.ui.home.MainActivity

class AuthActivity : AppCompatActivity(R.layout.activity_auth) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If token is set, finish activity.
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.token), "1|Is7kOeDGXOnEjcV5r6M65aZRFf3YyHwpKAs4rLmS")
            apply()
        }
        var token = sharedPref.getString(getString(R.string.token), null)
        token = "1|cYpZHYCdcL5HDY0LsVd1PriWMTZwSkhjeeoffEhY"
        val userId = 1
        if (token != null) {
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

    override fun onSupportNavigateUp(): Boolean {
        return false
    }

//    override fun launchMainActivity(userId: Int) {
//        val intent = Intent(applicationContext, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.putExtra(MainActivity.USER_ID, userId)
//        applicationContext.startActivity(intent)
//        setResult(Activity.RESULT_OK)
//        finish()
//    }
}