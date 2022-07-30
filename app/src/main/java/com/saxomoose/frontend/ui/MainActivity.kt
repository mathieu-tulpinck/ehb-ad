package com.saxomoose.frontend.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.saxomoose.frontend.R
import com.saxomoose.frontend.services.BackendApi
import com.saxomoose.frontend.services.BackendService
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            val response = BackendApi.retrofitService.getCategories()
            logging(response)
        }
    }
}

fun logging(message: String) {
    Log.v(TAG, message)
}