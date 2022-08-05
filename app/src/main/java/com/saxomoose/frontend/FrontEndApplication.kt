package com.saxomoose.frontend

import android.app.Application
import com.saxomoose.frontend.data.FrontEndDatabase

// Base class for maintaining global application state.
class FrontEndApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed rather than when the application starts.
    val database: FrontEndDatabase by lazy { FrontEndDatabase.getDatabase(this) }
}