package com.saxomoose.frontend.ui.home.events

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.models.Event
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("eventDate")
fun TextView.setDate(rawDate: Date) {
    @SuppressLint("SimpleDateFormat")
    this.text = SimpleDateFormat("yyyy-MM-dd").format(rawDate)
}