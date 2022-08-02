package com.saxomoose.frontend.models

// This wrapper around Category and Item object is used to pass data to the RecyclerView in the CatalogueFragment.
sealed class DataItem {

    data class ItemRow (val item: Item) : DataItem() {
        override val id = item.id
    }

    data class CategoryRow (val category: Category) : DataItem() {
        override val id  = category.id
    }

    abstract val id: Int
}