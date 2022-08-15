package com.saxomoose.frontend.ui.home.overview

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("transactionEntityId")
fun TextView.setText(id: Long) {
    this.text = id.toString()
}

@BindingAdapter("transactionItemEntityQuantity")
fun TextView.setText(quantity: Int) {
    this.text = quantity.toString()
}
