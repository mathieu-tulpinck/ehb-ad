package com.saxomoose.frontend.ui.home.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.databinding.FragmentEventsBinding
import com.saxomoose.frontend.ui.home.MainActivity.Companion.USER_ID

// Displays the user events.
class EventsFragment : Fragment() {
    // Binding object instance corresponding to the fragment_events.xml layout. This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks, when the view hierarchy is attached to the fragment.
    private lateinit var binding: FragmentEventsBinding
    private lateinit var eventsViewModel : EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO error handling.
        val userId = activity?.intent?.extras?.getInt("userId")!!
        val viewModel : EventsViewModel by viewModels { EventsViewModelFactory(userId)}
        eventsViewModel = viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = FragmentEventsBinding.inflate(inflater)

        return binding.root
    }

    // Inflates the layout with Data Binding, sets its lifecycle owner to the EventsFragment to enable data binding to observe LiveData, and sets up the RecyclerView with an adapter.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment.
        binding.lifecycleOwner = viewLifecycleOwner
        // Giving the binding access to the EventsViewModel.
        binding.viewModel = eventsViewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Sets the adapter of the RecyclerView.
        binding.recyclerView.adapter = EventAdapter(this)
        // Adds a divider between Event items.
        val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context,  LinearLayoutManager(requireContext()).orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }
}