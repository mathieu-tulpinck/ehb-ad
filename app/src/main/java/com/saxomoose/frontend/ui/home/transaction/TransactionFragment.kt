package com.saxomoose.frontend.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.R
import com.saxomoose.frontend.databinding.FragmentTransactionBinding
import com.saxomoose.frontend.models.TransactionItem

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
        if (viewModel.transactionItems.value.isNullOrEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.transactionFab.visibility = View.GONE
            binding.emptyDatasetTextview.text = String.format(getString(R.string.empty_dataset), "transaction items")
            binding.emptyDatasetTextview.visibility = View.VISIBLE

        } else {
            binding.emptyDatasetTextview.visibility = View.GONE
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = TransactionItemAdapter(this)
            val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context,  LinearLayoutManager(requireContext()).orientation)
            binding.recyclerView.addItemDecoration(dividerItemDecoration)
            binding.recyclerView.visibility = View.VISIBLE
            binding.transactionFab.visibility = View.VISIBLE
        }
    }

    // Calls shared ViewModel to decrease the quantity of the TransactionItem.
    fun removeItem(item: TransactionItem) {
        viewModel.removeItem(item)
        Toast.makeText(activity?.applicationContext, "${item.name} removed from transaction", Toast.LENGTH_LONG).show()
    }

    fun removeItemAndRedraw(item: TransactionItem) {
        viewModel.removeItem(item)
        // Hack to recreate fragment. Pops this fragment from the stack and navigate to itself.
        findNavController().navigate(R.id.fragment_transaction, arguments, NavOptions.Builder().setPopUpTo(R.id.fragment_transaction, true).build())
        Toast.makeText(activity?.applicationContext, "${item.name} removed from transaction", Toast.LENGTH_LONG).show()
    }
}