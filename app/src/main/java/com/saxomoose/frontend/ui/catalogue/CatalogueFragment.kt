package com.saxomoose.frontend.ui.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.databinding.FragmentCatalogueBinding
import com.saxomoose.frontend.databinding.FragmentEventsBinding
import com.saxomoose.frontend.ui.events.EventsViewModelFactory
import kotlin.properties.Delegates

// Displays the event items.
class CatalogueFragment : Fragment() {

    private var binding: FragmentCatalogueBinding? = null
    private var eventId : Int = -1
    private var viewModel : CatalogueViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { eventId = it.getInt("eventId") }
        val catalogueViewModel : CatalogueViewModel by viewModels { CatalogueViewModelFactory(eventId) }
        viewModel = catalogueViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        val fragmentBinding = FragmentCatalogueBinding.inflate(inflater)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = DataItemAdapter()
        val dividerItemDecoration = DividerItemDecoration(binding?.recyclerView?.context,  (binding?.recyclerView?.layoutManager as LinearLayoutManager).orientation)
        binding?.recyclerView?.addItemDecoration(dividerItemDecoration)
    }
}