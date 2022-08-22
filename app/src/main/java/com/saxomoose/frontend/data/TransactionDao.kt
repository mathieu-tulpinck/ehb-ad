package com.saxomoose.frontend.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saxomoose.frontend.entities.TransactionEntity
import com.saxomoose.frontend.entities.TransactionItemEntity
import com.saxomoose.frontend.entities.TransactionWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao
{
    // Not necessary to mark function as suspend.
    @androidx.room.Transaction
    @Query("SELECT * from transactions")
    fun getTransactionsWithItems(): Flow<List<TransactionWithItems>>

    // Creates transaction and batch inserts children transaction items in a single db transaction.
    @androidx.room.Transaction
    suspend fun insertTransactionWithItems(transactionItemEntities: List<TransactionItemEntity>) {
        val transactionEntity = TransactionEntity()
        val parent = insertTransaction(transactionEntity)
        for (entity in transactionItemEntities) {
            entity.transactionId = parent
            insertItem(entity)
        }
    }

    @Insert
    fun insertTransaction(transactionEntity: TransactionEntity): Long

    @Insert
    fun insertItem(transactionItemEntity: TransactionItemEntity): Long
}