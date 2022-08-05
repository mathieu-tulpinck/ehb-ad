package com.saxomoose.frontend

import android.app.Application
import com.saxomoose.frontend.data.FrontEndDatabase

// Base class for maintaining global application state.
class FrontEndApplication : Application() {
    val database : FrontEndDatabase by lazy { FrontEndDatabase.getDatabase(this) }
}