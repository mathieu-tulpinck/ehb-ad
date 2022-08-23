package com.saxomoose.frontend.ui.home.catalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.databinding.CategoryBinding
import com.saxomoose.frontend.databinding.ItemBinding
import com.saxomoose.frontend.models.Category
import com.saxomoose.frontend.models.CategoryWrapper
import com.saxomoose.frontend.models.Item

private const val CATEGORY = 0
private const val ITEM = 1

class CategoryAdapter(
    private val fragment: CatalogueFragment
) : ListAdapter<CategoryWrapper, RecyclerView.ViewHolder>(DiffCallback) {
    class CategoryViewHolder(
        private var binding: CategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    class ItemViewHolder(
        private var binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }

        val button = binding.button
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CategoryWrapper>() {
        override fun areItemsTheSame(oldItem: CategoryWrapper, newItem: CategoryWrapper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoryWrapper,
            newItem: CategoryWrapper
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CategoryWrapper.CategoryRow -> CATEGORY
            is CategoryWrapper.ItemRow -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CATEGORY -> CategoryViewHolder(
                CategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM -> ItemViewHolder(
                ItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                val categoryRow = getItem(position) as CategoryWrapper.CategoryRow
                holder.bind(categoryRow.category)
            }
            is ItemViewHolder -> {
                val itemRow = getItem(position) as CategoryWrapper.ItemRow
                holder.bind(itemRow.item)
                holder.button.setOnClickListener {
                    fragment.addItem(itemRow.item)
                }
            }
        }
    }
}