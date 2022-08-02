package com.saxomoose.frontend.ui.catalogue

import androidx.lifecycle.*
import com.saxomoose.frontend.models.Category
import com.saxomoose.frontend.services.BackendApi
import kotlinx.coroutines.launch

class CatalogueViewModelFactory(private val eventId: Int): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CatalogueViewModel(eventId) as T
}

class CatalogueViewModel(eventId: Int) : ViewModel() {
    private var _categories = MutableLiveData<List<Category>>()
    val categories : LiveData<List<Category>> = _categories

    init {
        getEventCategories(eventId)
    }

    private fun getEventCategories(eventId: Int) {
        viewModelScope.launch {
            try {
                _categories.value = BackendApi.retrofitService.getEventCategories(eventId)
            } catch (e: Exception) {
                _categories.value = listOf()
            }
        }
    }
}