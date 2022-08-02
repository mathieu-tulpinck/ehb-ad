package com.saxomoose.frontend.ui.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.saxomoose.frontend.databinding.FragmentTransactionBinding
import com.saxomoose.frontend.models.TransactionItem
import com.saxomoose.frontend.ui.TransactionItemAdapter
import com.saxomoose.frontend.ui.TransactionViewModel

class TransactionFragment : Fragment() {
    private var binding: FragmentTransactionBinding? = null
    private val viewModel : TransactionViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        val fragmentBinding = FragmentTransactionBinding.inflate(inflater)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
       binding?.recyclerView?.adapter = TransactionItemAdapter(this)

    }

    // Calls shared ViewModel to decrease the quantity of the TransactionItem.
    fun removeItem(item: TransactionItem) {
        viewModel.removeItem(item)
        // binding?.recyclerView?.adapter?.notifyDataSetChanged()
    }
}