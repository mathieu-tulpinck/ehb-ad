package com.saxomoose.frontend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_items")
class TransactionItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var transactionId: Long,
    val name: String,
    val quantity: Int
)