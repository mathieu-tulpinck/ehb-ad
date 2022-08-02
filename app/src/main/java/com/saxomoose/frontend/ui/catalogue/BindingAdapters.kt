package com.saxomoose.frontend.ui.catalogue

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.models.Category
import com.saxomoose.frontend.models.DataItem

@BindingAdapter("items")
fun bindRecyclerView(recyclerView: RecyclerView, categories: List<Category>?) {
    val adapter = recyclerView.adapter as DataItemAdapter
    val data = mutableListOf<DataItem>()
    if (categories != null) {
        for (category in categories) {
            data.add(DataItem.CategoryRow(category))
            for (item in category.items) {
                data.add(DataItem.ItemRow(item))
            }
        }
    }
    adapter.submitList(data)
}
