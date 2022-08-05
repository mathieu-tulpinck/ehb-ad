package com.saxomoose.frontend.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saxomoose.frontend.entities.Transaction
import com.saxomoose.frontend.entities.TransactionItem
import com.saxomoose.frontend.entities.TransactionWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    // Not necessary to mark function as suspend.
    @androidx.room.Transaction
    @Query("SELECT * from transactions")
    fun getTransactionsWithItems(): Flow<List<TransactionWithItems>>

    @androidx.room.Transaction
    suspend fun insertTransactionWithItems(transaction: Transaction, transactionItems: List<TransactionItem>) {
        val parent = insertTransaction(transaction)
        for (item in transactionItems) {
            item.transactionId = parent
            insertItem(item)
        }
    }

    @Insert
    fun insertTransaction(transaction: Transaction) : Long

    @Insert
    fun insertItem(transactionItem: TransactionItem) : Long
}