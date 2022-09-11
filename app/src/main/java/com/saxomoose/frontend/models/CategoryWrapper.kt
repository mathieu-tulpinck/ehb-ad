package com.saxomoose.frontend.models

// This wrapper around Category and Item object is used to pass data to the RecyclerView in the CatalogueFragment.
sealed class CategoryWrapper {
    data class ItemRow(val item: Item) : CategoryWrapper() {
        override val id = item.id
    }

    data class CategoryRow(val category: Category) : CategoryWrapper() {
        override val id = category.id
    }

    abstract val id: Int
}