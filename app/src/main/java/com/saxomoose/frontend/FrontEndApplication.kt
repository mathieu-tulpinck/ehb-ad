package com.saxomoose.frontend

import android.app.Application
import com.saxomoose.frontend.data.FrontEndDatabase
import com.saxomoose.frontend.services.BackendApi
import com.saxomoose.frontend.services.BackendService

// Base class for maintaining global application state.
class FrontEndApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed rather than when the application starts.
    val database: FrontEndDatabase by lazy { FrontEndDatabase.getDatabase(this) }
    val backendService: BackendService by lazy { BackendApi.getService(this, false) }
    val backendServiceWithToken: BackendService by lazy { BackendApi.getService(this, true) }
}