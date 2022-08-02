package com.saxomoose.frontend.ui.catalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.databinding.CategoryBinding
import com.saxomoose.frontend.databinding.ItemBinding
import com.saxomoose.frontend.models.Category
import com.saxomoose.frontend.models.DataItem
import com.saxomoose.frontend.models.Item
import java.lang.ClassCastException

private const val CATEGORY = 0
private const val ITEM = 1

class DataItemAdapter(private val fragment: CatalogueFragment) : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    class CategoryViewHolder(private var binding: CategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    class ItemViewHolder(private var binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fragment: CatalogueFragment, item: Item, ) {
            binding.catalogueFragment = fragment
            binding.item = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            CATEGORY -> CategoryViewHolder(CategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ITEM -> ItemViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.CategoryRow -> CATEGORY
            is DataItem.ItemRow -> ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CategoryViewHolder -> {
                val categoryRow = getItem(position) as DataItem.CategoryRow
                holder.bind(categoryRow.category)
            }
            is ItemViewHolder -> {
                val itemRow = getItem(position) as DataItem.ItemRow
                holder.bind(fragment, itemRow.item)
            }
        }
    }
}