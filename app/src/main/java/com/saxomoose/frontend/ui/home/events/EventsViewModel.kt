package com.saxomoose.frontend.ui.home.events

import androidx.lifecycle.*
import com.saxomoose.frontend.models.Event
import com.saxomoose.frontend.services.BackendApi
import com.saxomoose.frontend.services.BackendService
import kotlinx.coroutines.launch

private const val TAG = "EventsViewModel"

class EventsViewModelFactory(
    private val webService: BackendService,
    private val userId: Int
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EventsViewModel(webService, userId) as T
}

// The ViewModel attached to the EventsFragment.
class EventsViewModel(
    private val webService: BackendService,
    userId: Int
) : ViewModel() {
    // Internally, we use a mutable variable, because we will be updating the list of events with new values.
    private var _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    init {
        getUserEvents(userId)
    }

    // Gets the events from the BackendService and updates the list of events.
    private fun getUserEvents(userId: Int) {
        viewModelScope.launch {
            try {
                _events.value =
                    webService.getUserEvents(userId) // Log.v(TAG, _events.toString())
            } catch (e: Exception) {
                _events.value = listOf()
            }
        }
    }
}