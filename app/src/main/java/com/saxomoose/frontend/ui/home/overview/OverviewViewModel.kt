package com.saxomoose.frontend.ui.home.overview

import androidx.lifecycle.*
import com.saxomoose.frontend.data.TransactionDao
import com.saxomoose.frontend.entities.TransactionWithItems
import java.lang.IllegalArgumentException

class OverviewViewModelFactory(
        private val transactionDao: TransactionDao
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return OverviewViewModel(transactionDao) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}

class OverviewViewModel(transactionDao: TransactionDao) : ViewModel() {
    // Data source is database.
    val transactions: LiveData<List<TransactionWithItems>> = transactionDao.getTransactionsWithItems()
            .asLiveData()
}