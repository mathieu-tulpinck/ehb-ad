package com.saxomoose.frontend.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.R
import com.saxomoose.frontend.databinding.ProcessButtonBinding
import com.saxomoose.frontend.databinding.TransactionItemBinding
import com.saxomoose.frontend.models.TransactionItem
import com.saxomoose.frontend.ui.home.transaction.TransactionFragment
import kotlinx.coroutines.NonDisposableHandle.parent
import java.lang.ClassCastException

private const val ITEM = 0
private const val BUTTON = 1

class TransactionItemAdapter(
    private val transactionFragment: TransactionFragment
) : ListAdapter<TransactionItem, RecyclerView.ViewHolder>(
    DiffCallback
){

    class TransactionItemViewHolder(private var binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionItem: TransactionItem) {
            binding.transactionItem = transactionItem
            binding.executePendingBindings()
        }

        val button = binding.button
    }

    class ProcessButtonViewHolder(binding : ProcessButtonBinding) : RecyclerView.ViewHolder(binding.root) { }

    companion object DiffCallback : DiffUtil.ItemCallback<TransactionItem>() {
        override fun areItemsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int) : Int {
        return when (getItem(position)) {
            is TransactionItem -> ITEM
            null -> BUTTON
            else -> throw ClassCastException("Unknown viewType at $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM -> TransactionItemViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            BUTTON -> ProcessButtonViewHolder(ProcessButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is TransactionItemViewHolder -> {
                val transactionItem = getItem(position)
                holder.bind(transactionItem)
                holder.button.setOnClickListener {
                transactionFragment.removeItem(transactionItem)
                this.notifyItemChanged(position)
                }
            }
        }
    }
}