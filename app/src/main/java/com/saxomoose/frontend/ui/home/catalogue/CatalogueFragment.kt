package com.saxomoose.frontend.ui.home.catalogue

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.R
import com.saxomoose.frontend.databinding.FragmentCatalogueBinding
import com.saxomoose.frontend.models.Item
import com.saxomoose.frontend.ui.home.MenuItemSelector
import com.saxomoose.frontend.ui.home.TransactionViewModel

// Displays the event items.
class CatalogueFragment : Fragment() {

    private lateinit var binding: FragmentCatalogueBinding
    private var eventId : Int = -1
    private lateinit var catalogueViewModel : CatalogueViewModel
    private val transactionViewModel : TransactionViewModel by activityViewModels()
    private lateinit var token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve eventId supplied at fragment creation.
        arguments?.let { eventId = it.getInt("eventId") }
        // Retrieve token from SharedPreferences.
        val sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        token = sharedPref?.getString(getString(R.string.token), null).toString()
        val viewModel : CatalogueViewModel by viewModels { CatalogueViewModelFactory(token, eventId) }
        catalogueViewModel = viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = FragmentCatalogueBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // CatalogueViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.catalogueViewModel = catalogueViewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = DataItemAdapter(this)
        val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context,  LinearLayoutManager(requireContext()).orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        // TransactionViewModel
        binding.transactionViewModel = transactionViewModel

        // Sync BottomNavigationView.
        val activity = activity as MenuItemSelector
        activity.selectEventsMenuItem()
    }

    // Calls shared ViewModel to increase the quantity of the TransactionItem.
    fun addItem(item: Item) {
        transactionViewModel.addItem(item)
        Toast.makeText(activity?.applicationContext, "${item.name} added to Transaction", Toast.LENGTH_LONG).show()
    }

}