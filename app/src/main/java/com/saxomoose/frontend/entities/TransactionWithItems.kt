package com.saxomoose.frontend.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.saxomoose.frontend.entities.Transaction
import com.saxomoose.frontend.entities.TransactionItem

data class TransactionWithItems (
    @Embedded
    val transaction: Transaction,
    @Relation(parentColumn = "id", entityColumn = "id")
    val items : List<TransactionItem>
) { }