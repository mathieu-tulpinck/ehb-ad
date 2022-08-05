package com.saxomoose.frontend.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithItems (
    @Embedded
    val transactionEntity: TransactionEntity,
    @Relation(parentColumn = "id", entityColumn = "id")
    val itemEntities : List<TransactionItemEntity>
) { }