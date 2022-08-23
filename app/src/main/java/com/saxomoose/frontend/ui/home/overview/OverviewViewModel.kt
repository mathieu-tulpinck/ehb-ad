package com.saxomoose.frontend.ui.home.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.saxomoose.frontend.data.TransactionDao
import com.saxomoose.frontend.entities.TransactionWithItems

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
    val transactions: LiveData<List<TransactionWithItems>> =
        transactionDao.getTransactionsWithItems()
            .asLiveData()
}