package com.saxomoose.frontend.ui.home.overview

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.entities.TransactionWithItems
import com.saxomoose.frontend.models.TransactionWrapper

@BindingAdapter("transactions")
fun bindRecyclerView(recyclerView: RecyclerView, transactions: List<TransactionWithItems>?) {
    val adapter = recyclerView.adapter as TransactionAdapter
    val data = mutableListOf<TransactionWrapper>()
    if (transactions != null) {
        for (transaction in transactions) {
            data.add(TransactionWrapper.TransactionRow(transaction.transactionEntity))
            for (item in transaction.itemEntities) {
                data.add(TransactionWrapper.TransactionItemRow(item))
            }
        }
    }
    adapter.submitList(data)
}

class BindingAdapters {
    @BindingAdapter("transactionEntityId")
    fun TextView.setText(id: Long) {
        this.text = id.toString()
    }
}