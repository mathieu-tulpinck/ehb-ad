package com.saxomoose.frontend.ui.home.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.databinding.EventBinding
import com.saxomoose.frontend.models.Event

// This class implements a RecyclerView ListAdapter which uses data binding to present List data, including computing diffs between Lists.
class EventAdapter(
        private val fragment: Fragment
    ) : ListAdapter<Event, EventAdapter.EventViewHolder>(DiffCallback) {
    // The constructor takes the binding variable from the associated event.
    class EventViewHolder(private var binding: EventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }

        val button = binding.button
    }

    // Allows the RecyclerView to determine which items have changed when the list of events has been updated.
    companion object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    // Creates new RecyclerView item views (invoked by the layout manager).
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // Updates the contents of the list item to reflect the item at the given position (invoked by the layout manager).
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.button.setOnClickListener {
            val action = EventsFragmentDirections.actionFragmentEventsToFragmentCatalogue(eventId = event.id)
            findNavController(fragment).navigate(action)
        }
    }
}
