package com.saxomoose.frontend.ui

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.models.Event
import com.saxomoose.frontend.models.TransactionItem
import com.saxomoose.frontend.ui.events.EventAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("transactionItems")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<TransactionItem>?) {
    val adapter = recyclerView.adapter as TransactionItemAdapter
    adapter.submitList(data)
}

@BindingAdapter("transactionItemQuantity")
fun TextView.setText(quantity: Int) {
    this.text = quantity.toString()
}
