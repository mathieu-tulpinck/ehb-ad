package com.saxomoose.frontend.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.databinding.FragmentTransactionBinding
import com.saxomoose.frontend.models.TransactionItem
import com.saxomoose.frontend.ui.home.TransactionItemAdapter
import com.saxomoose.frontend.ui.home.TransactionViewModel

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private val viewModel : TransactionViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = FragmentTransactionBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = TransactionItemAdapter(this)
        val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context,  LinearLayoutManager(requireContext()).orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

    }

    // Calls shared ViewModel to decrease the quantity of the TransactionItem.
    fun removeItem(item: TransactionItem) {
        viewModel.removeItem(item)
        Toast.makeText(activity?.applicationContext, "${item.name} remove from Transaction", Toast.LENGTH_LONG).show()
    }
}