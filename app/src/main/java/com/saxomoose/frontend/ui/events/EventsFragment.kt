package com.saxomoose.frontend.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.databinding.FragmentEventsBinding


// TODO get userId from login activity.
private const val USER_ID = 1

// Displays the user events.
class EventsFragment : Fragment() {
    // Binding object instance corresponding to the fragment_events.xml layout. This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks, when the view hierarchy is attached to the fragment.
    private var binding: FragmentEventsBinding? = null
    private val viewModel : EventsViewModel by viewModels { EventsViewModelFactory(USER_ID) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        val fragmentBinding = FragmentEventsBinding.inflate(inflater)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    // Inflates the layout with Data Binding, sets its lifecycle owner to the EventsFragment to enable data binding to observe LiveData, and sets up the RecyclerView with an adapter.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment.
        binding?.lifecycleOwner = this
        // Giving the binding access to the EventsViewModel.
        binding?.viewModel = viewModel
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        // Sets the adapter of the RecyclerView.
        binding?.recyclerView?.adapter = EventAdapter(this)
        val dividerItemDecoration = DividerItemDecoration(binding?.recyclerView?.context,  (binding?.recyclerView?.layoutManager as LinearLayoutManager).orientation)
        binding?.recyclerView?.addItemDecoration(dividerItemDecoration)
        // TODO why? Inspired by Cupcake.
        // binding?.eventsFragment = this
    }
}