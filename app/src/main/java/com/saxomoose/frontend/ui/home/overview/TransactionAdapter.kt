package com.saxomoose.frontend.ui.home.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.databinding.TransactionEntityBinding
import com.saxomoose.frontend.databinding.TransactionItemEntityBinding
import com.saxomoose.frontend.entities.TransactionEntity
import com.saxomoose.frontend.entities.TransactionItemEntity
import com.saxomoose.frontend.models.TransactionWrapper
import java.lang.ClassCastException

private const val TRANSACTION = 0
private const val ITEM = 1

class TransactionAdapter : ListAdapter<TransactionWrapper, RecyclerView.ViewHolder>(DiffCallback) {
    class TransactionEntityViewHolder(
            private var binding: TransactionEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionEntity: TransactionEntity) {
            binding.transactionEntity = transactionEntity
            binding.executePendingBindings()
        }
    }

    class TransactionItemEntityViewHolder(
            private var binding: TransactionItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionItemEntity: TransactionItemEntity) {
            binding.transactionItemEntity = transactionItemEntity
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TransactionWrapper>() {
        override fun areItemsTheSame(oldItem: TransactionWrapper, newItem: TransactionWrapper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TransactionWrapper, newItem: TransactionWrapper): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TransactionWrapper.TransactionRow -> TRANSACTION
            is TransactionWrapper.TransactionItemRow -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TRANSACTION -> TransactionEntityViewHolder(TransactionEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ITEM -> TransactionItemEntityViewHolder(TransactionItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionEntityViewHolder -> {
                val transactionRow = getItem(position) as TransactionWrapper.TransactionRow
                holder.bind(transactionRow.transactionEntity)
            }
            is TransactionItemEntityViewHolder -> {
                val transactionItemRow = getItem(position) as TransactionWrapper.TransactionItemRow
                holder.bind(transactionItemRow.transactionItemEntity)
            }
        }
    }
}