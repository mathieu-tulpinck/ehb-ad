package com.saxomoose.frontend.models

import java.util.*

data class Event(
    val id: Int,
    //    @Json(name = "user_id") val userId : Int,
    //    @Json(name = "bank_account_id") val bankAccountId : Int,
    val name: String,
    val date: Date
)
