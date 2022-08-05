package com.saxomoose.frontend.ui.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.models.TransactionItem
import com.saxomoose.frontend.ui.home.transaction.TransactionItemAdapter

@BindingAdapter("transactionItems")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<TransactionItem>?) {
    if (!data.isNullOrEmpty()) {
        val adapter = recyclerView.adapter as TransactionItemAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("transactionItemQuantity")
fun TextView.setText(quantity: Int) {
    this.text = quantity.toString()
}
