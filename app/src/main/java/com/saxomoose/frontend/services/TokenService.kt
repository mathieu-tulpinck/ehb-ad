package com.saxomoose.frontend.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.saxomoose.frontend.R

// TODO: token should be retrieved from service and passed to BackendApi.
class TokenService : Service()
{
    var token: String

    init {
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        token = sharedPref.getString(getString(R.string.token), null).toString()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}