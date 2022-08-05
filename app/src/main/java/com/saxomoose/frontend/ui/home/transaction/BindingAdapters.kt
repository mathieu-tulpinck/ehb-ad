package com.saxomoose.frontend.ui.home.transaction

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.models.TransactionItem

@BindingAdapter("transactionItemQuantity")
fun TextView.setText(quantity: Int) {
    this.text = quantity.toString()
}
