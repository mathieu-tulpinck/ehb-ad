package com.saxomoose.frontend.models

import com.squareup.moshi.Json

data class Category(
    val id: Int,
    @Json(name = "event_id")
    val eventId: Int,
    val name: String,
    val items: List<Item>
)