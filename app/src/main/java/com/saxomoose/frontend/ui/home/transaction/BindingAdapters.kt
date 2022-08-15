package com.saxomoose.frontend.ui.home.transaction

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("transactionItemQuantity")
fun TextView.setText(quantity: Int) {
    this.text = quantity.toString()
}
