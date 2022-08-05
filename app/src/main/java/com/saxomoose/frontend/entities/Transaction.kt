package com.saxomoose.frontend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saxomoose.frontend.models.TransactionItem

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    // val uploaded: Boolean,
) { }