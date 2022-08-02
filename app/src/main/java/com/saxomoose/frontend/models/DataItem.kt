package com.saxomoose.frontend.models

import com.squareup.moshi.Json
import java.util.*

sealed class DataItem {

    data class ItemRow (val item: Item) : DataItem() {
        override val id = item.id
    }

    data class CategoryRow (val category: Category) : DataItem() {
        override val id  = category.id
    }

    abstract val id: Int
}