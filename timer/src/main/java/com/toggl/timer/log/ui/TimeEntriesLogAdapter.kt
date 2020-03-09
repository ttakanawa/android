package com.toggl.timer.log.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.toggl.timer.R
import com.toggl.timer.log.domain.FlatTimeEntryItem
import com.toggl.timer.log.domain.TimeEntryViewModel

class TimeEntryLogAdapter(private val onContinueTappedListener: (Long) -> Unit = {}) :
    ListAdapter<TimeEntryViewModel, TimeEntryLogViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryLogViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.time_entries_log_item, parent, false)
            .let { TimeEntryLogViewHolder(it, onContinueTappedListener) }

    override fun onBindViewHolder(holder: TimeEntryLogViewHolder, position: Int) {
        holder.bind(getItem(position) as FlatTimeEntryItem)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<TimeEntryViewModel>() {
            override fun areItemsTheSame(
                oldItem: TimeEntryViewModel,
                newItem: TimeEntryViewModel
            ): Boolean =
                oldItem is FlatTimeEntryItem &&
                    newItem is FlatTimeEntryItem &&
                    oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TimeEntryViewModel,
                newItem: TimeEntryViewModel
            ): Boolean =
                oldItem is FlatTimeEntryItem &&
                    newItem is FlatTimeEntryItem &&
                    oldItem.description == newItem.description
        }
    }
}
