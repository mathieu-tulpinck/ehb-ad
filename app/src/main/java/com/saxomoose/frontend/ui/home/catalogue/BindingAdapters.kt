package com.saxomoose.frontend.ui.home.catalogue

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.models.Category
import com.saxomoose.frontend.models.CategoryWrapper
import java.text.NumberFormat
import java.util.*

@BindingAdapter("items")
fun bindRecyclerView(recyclerView: RecyclerView, categories: List<Category>?) {
    val adapter = recyclerView.adapter as CategoryAdapter
    val data = mutableListOf<CategoryWrapper>()
    if (categories != null) {
        for (category in categories) {
            data.add(CategoryWrapper.CategoryRow(category))
            for (item in category.items) {
                data.add(CategoryWrapper.ItemRow(item))
            }
        }
    }
    adapter.submitList(data)
}

@BindingAdapter("itemPrice")
fun TextView.setText(price: Double) {
    val formatter = NumberFormat.getInstance(Locale.FRENCH)
    @SuppressLint("SetTextI18n")
    this.text = "${formatter.format(price)} €"
    // "${String.format("%.2f", price)} €"
}
