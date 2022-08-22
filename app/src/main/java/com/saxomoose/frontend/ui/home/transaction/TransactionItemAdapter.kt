package com.saxomoose.frontend.ui.home.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.databinding.TransactionItemBinding
import com.saxomoose.frontend.models.TransactionItem

class TransactionItemAdapter(
        private val transactionFragment: TransactionFragment
    ) : ListAdapter<TransactionItem, TransactionItemAdapter.TransactionItemViewHolder>(DiffCallback) {
    class TransactionItemViewHolder(
            private var binding: TransactionItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionItem: TransactionItem) {
            binding.transactionItem = transactionItem
            binding.executePendingBindings()
        }

        val button = binding.button
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TransactionItem>() {
        override fun areItemsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
        return TransactionItemViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TransactionItemViewHolder, position: Int) {
        val transactionItem = getItem(position)
        holder.bind(transactionItem)
        if (position == 0) {
            holder.button.setOnClickListener {
                transactionFragment.removeItemAndRedraw(transactionItem)
            }
        } else {
            holder.button.setOnClickListener {
                transactionFragment.removeItem(transactionItem)
                notifyItemChanged(position)
            }
        }
    }
}