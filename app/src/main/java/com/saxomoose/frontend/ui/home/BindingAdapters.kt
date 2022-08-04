package com.saxomoose.frontend.ui.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.models.TransactionItem

@BindingAdapter("transactionItems")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<TransactionItem?>?) {
    val adapter = recyclerView.adapter as TransactionItemAdapter
    val dataCopy = data?.toMutableList()
    dataCopy?.add(null)
    adapter.submitList(dataCopy)
}

@BindingAdapter("transactionItemQuantity")
fun TextView.setText(quantity: Int) {
    this.text = quantity.toString()
}
