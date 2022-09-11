package com.saxomoose.frontend.ui.home.events

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.FrontEndApplication
import com.saxomoose.frontend.R
import com.saxomoose.frontend.databinding.FragmentEventsBinding

// Displays the user events.
class EventsFragment : Fragment() {
    // Binding object instance corresponding to the fragment_events.xml layout. This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks, when the view hierarchy is attached to the fragment.
    private lateinit var binding: FragmentEventsBinding
    private lateinit var eventsViewModel: EventsViewModel
    private var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieves userId from SharedPreferences.
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        userId = sharedPref?.getInt(getString(R.string.userId), -1)
        if (userId != null && userId != -1) {
            val viewModel: EventsViewModel by viewModels {
                EventsViewModelFactory(
                    (activity?.application as FrontEndApplication).backendServiceAuth,
                    userId!!
                )
            }
            eventsViewModel = viewModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = EventAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        eventsViewModel.events.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }
    }
}