package com.saxomoose.frontend.ui.home.transaction

import androidx.lifecycle.*
import com.saxomoose.frontend.data.TransactionDao
import com.saxomoose.frontend.entities.TransactionEntity
import com.saxomoose.frontend.entities.TransactionItemEntity
import com.saxomoose.frontend.models.Item
import com.saxomoose.frontend.models.TransactionItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TransactionViewModelFactory(private val transactionDao: TransactionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(transactionDao) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}

class TransactionViewModel(private val transactionDao: TransactionDao) : ViewModel() {

    private var _transactionItems = MutableLiveData<List<TransactionItem>>()
    val transactionItems : LiveData<List<TransactionItem>> = _transactionItems

    // TODO reset transaction logic.

    fun addItem(item: Item) {
        var currentList = _transactionItems.value?.toMutableList()
        if (currentList == null) {
            currentList = mutableListOf()
        }
        val transactionItem = currentList.firstOrNull { it.id == item.id}
        if (transactionItem == null) {
            val newTransactionItem = TransactionItem(item.id, item.name, 1)
            currentList.add(newTransactionItem)
            _transactionItems.value = currentList.toList()
        } else {
            transactionItem.quantity++
        }
    }

    fun removeItem(transactionItem: TransactionItem) {
        val currentList = _transactionItems.value?.toMutableList()
        val currentTransactionItem = currentList?.firstOrNull { it.id == transactionItem.id}
        if (currentTransactionItem != null) {
            if (currentTransactionItem.quantity > 1) {
                currentTransactionItem.quantity--
            } else {
                currentList.remove(currentTransactionItem)
                _transactionItems.value = currentList.toList()
            }
        }
    }

    fun saveTransaction(transactionItemEntities: List<TransactionItemEntity>) {
        viewModelScope.launch {
            transactionDao.insertTransactionWithItems(transactionItemEntities)
        }
    }


}