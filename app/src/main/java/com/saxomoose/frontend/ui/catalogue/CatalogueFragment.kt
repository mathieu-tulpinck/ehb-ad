package com.saxomoose.frontend.ui.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.databinding.FragmentCatalogueBinding
import com.saxomoose.frontend.models.Item
import com.saxomoose.frontend.ui.MenuItemSelector
import com.saxomoose.frontend.ui.TransactionViewModel

// Displays the event items.
class CatalogueFragment : Fragment() {

    private var binding: FragmentCatalogueBinding? = null
    private var eventId : Int = -1
    private var catalogueViewModel : CatalogueViewModel? = null
    private val transactionViewModel : TransactionViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { eventId = it.getInt("eventId") }
        val viewModel : CatalogueViewModel by viewModels { CatalogueViewModelFactory(eventId) }
        catalogueViewModel = viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        val fragmentBinding = FragmentCatalogueBinding.inflate(inflater)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // CatalogueViewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.catalogueViewModel = catalogueViewModel
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = DataItemAdapter(this)
        val dividerItemDecoration = DividerItemDecoration(binding?.recyclerView?.context,  LinearLayoutManager(requireContext()).orientation)
        binding?.recyclerView?.addItemDecoration(dividerItemDecoration)

        // TransactionViewModel
        binding?.transactionViewModel = transactionViewModel

        // Sync BottomNavigationView.
        val activity = activity as MenuItemSelector
        activity.selectEventsMenuItem()
    }

    // Calls shared ViewModel to increase the quantity of the TransactionItem.
    fun addItem(item: Item) {
        transactionViewModel.addItem(item)
    }

}