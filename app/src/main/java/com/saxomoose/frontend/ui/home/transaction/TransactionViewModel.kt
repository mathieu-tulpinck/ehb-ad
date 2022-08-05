package com.saxomoose.frontend.ui.home.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saxomoose.frontend.models.Item
import com.saxomoose.frontend.models.TransactionItem

class TransactionViewModel : ViewModel() {

    private var _transactionItems = MutableLiveData<List<TransactionItem>>()
    val transactionItems : LiveData<List<TransactionItem>> = _transactionItems

    // Reset transaction logic.

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
}