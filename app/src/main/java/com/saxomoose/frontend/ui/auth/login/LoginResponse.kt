package com.saxomoose.frontend.ui.auth.login

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "user_id")
    val userId: Int,
    val token: String
    )