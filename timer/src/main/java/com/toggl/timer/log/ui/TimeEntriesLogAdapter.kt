package com.toggl.timer.log.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toggl.timer.R
import com.toggl.timer.log.domain.FlatTimeEntryItem
import com.toggl.timer.log.domain.TimeEntryViewModel
import java.util.concurrent.TimeUnit

class TimeEntryLogAdapter(val onContinueTappedListener: (Long) -> Unit = {})
    : ListAdapter<TimeEntryViewModel, TimeEntryLogAdapter.TimeEntryLogViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryLogViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.time_entries_log_item, parent, false)
            .let(::TimeEntryLogViewHolder)

    override fun onBindViewHolder(holder: TimeEntryLogViewHolder, position: Int) {
        holder.bind(getItem(position) as FlatTimeEntryItem)
    }

    inner class TimeEntryLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addDescriptionLabel = itemView.findViewById<View>(R.id.add_description_label)
        private val description = itemView.findViewById<TextView>(R.id.description)
        private val duration = itemView.findViewById<TextView>(R.id.duration)
        private val continueButton = itemView.findViewById<View>(R.id.continue_button)

        fun bind(item: FlatTimeEntryItem) {

            val hasDescription = item.description.isNotBlank()
            addDescriptionLabel.isVisible = !hasDescription
            description.isVisible = hasDescription
            description.text = item.description

            item.duration?.let { durationMillis ->
                val hms = String.format(
                    "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(durationMillis),
                    TimeUnit.MILLISECONDS.toMinutes(durationMillis) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(durationMillis) % TimeUnit.MINUTES.toSeconds(1)
                )
                duration.text = hms
            }

            continueButton.setOnClickListener {
                onContinueTappedListener(item.id)
            }
        }
    }

    companion object {
        val diffCallback =  object : DiffUtil.ItemCallback<TimeEntryViewModel>() {
            override fun areItemsTheSame(
                oldItem: TimeEntryViewModel,
                newItem: TimeEntryViewModel
            ): Boolean =
                oldItem is FlatTimeEntryItem &&
                        newItem is FlatTimeEntryItem &&
                        oldItem.id  == newItem.id

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
