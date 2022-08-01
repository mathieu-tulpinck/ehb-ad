package com.saxomoose.frontend.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saxomoose.frontend.databinding.ItemEventBinding
import com.saxomoose.frontend.models.Event
import com.saxomoose.frontend.viewmodels.EventsViewModel

// This class implements a RecyclerView ListAdapter which uses data binding to present List data, including computing diffs between Lists.
class EventAdapter : ListAdapter<Event, EventAdapter.EventsViewHolder>(DiffCallback) {
    // The constructor takes the binding variable from the associated ItemEvent.
    class EventsViewHolder(private var binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }
    }

    // Allows the RecyclerView to determine which items have changed when the List of Event has been updated.
    companion object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.name == newItem.name
        }
    }

    // Create new RecyclerView item views (invoked by the layout manager).
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(ItemEventBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }
}
