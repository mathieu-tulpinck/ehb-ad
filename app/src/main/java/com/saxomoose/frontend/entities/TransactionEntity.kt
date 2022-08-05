package com.saxomoose.frontend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saxomoose.frontend.models.TransactionItem

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    // val uploaded: Boolean,
)