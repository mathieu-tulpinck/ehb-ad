package com.saxomoose.frontend.models

import com.saxomoose.frontend.entities.TransactionEntity
import com.saxomoose.frontend.entities.TransactionItemEntity

sealed class TransactionWrapper
{
    data class TransactionRow(val transactionEntity: TransactionEntity) : TransactionWrapper()
    {
        override val id = transactionEntity.id
    }

    data class TransactionItemRow(val transactionItemEntity: TransactionItemEntity) : TransactionWrapper()
    {
        override val id = transactionItemEntity.id
    }

    abstract val id: Long
}