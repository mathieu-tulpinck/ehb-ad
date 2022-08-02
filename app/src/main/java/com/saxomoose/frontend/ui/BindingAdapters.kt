package com.saxomoose.frontend.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.models.TransactionItem

@BindingAdapter("transactionItems")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<TransactionItem>?) {
    val adapter = recyclerView.adapter as TransactionItemAdapter
    adapter.submitList(data)
}

@BindingAdapter("transactionItemQuantity")
fun TextView.setText(quantity: Int) {
    this.text = quantity.toString()
}
