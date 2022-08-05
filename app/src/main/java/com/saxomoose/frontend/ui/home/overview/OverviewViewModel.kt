package com.saxomoose.frontend.ui.home.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saxomoose.frontend.data.TransactionDao
import java.lang.IllegalArgumentException

class OverviewViewModelFactory(private val transactionDao: TransactionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(transactionDao) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}

class OverviewViewModel(private val transactionDao: TransactionDao) : ViewModel() {
}