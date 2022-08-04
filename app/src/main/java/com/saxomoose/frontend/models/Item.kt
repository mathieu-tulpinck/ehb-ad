package com.saxomoose.frontend.models

import com.squareup.moshi.Json

data class Item (
    val id : Int,
    @Json(name = "category_id")
    val categoryId: Int,
    val name : String,
    val price : Double,
    @Json(name = "vat_rate")
    val vatRate : Int
){ }