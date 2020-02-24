package com.toggl.timer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toggl.architecture.core.Store
import com.toggl.architecture.extensions.addTo
import com.toggl.timer.R
import com.toggl.timer.di.TimerComponentProvider
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.states.FlatTimeEntryItem
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimeEntryLogViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.time_entries_log_fragment.*
import javax.inject.Inject

class TimeEntriesLogFragment : Fragment(R.layout.time_entries_log_fragment) {

    private val adapter = TimeEntryLogAdapter()

    class TimeEntryLogAdapter : ListAdapter<TimeEntryLogViewModel, TimeEntryLogViewHolder>(
        object : DiffUtil.ItemCallback<TimeEntryLogViewModel>() {
            override fun areItemsTheSame(
                oldItem: TimeEntryLogViewModel,
                newItem: TimeEntryLogViewModel
            ): Boolean =
                oldItem is FlatTimeEntryItem &&
                newItem is FlatTimeEntryItem &&
                oldItem.id  == newItem.id

            override fun areContentsTheSame(
                oldItem: TimeEntryLogViewModel,
                newItem: TimeEntryLogViewModel
            ): Boolean =
                oldItem is FlatTimeEntryItem &&
                newItem is FlatTimeEntryItem &&
                oldItem.description == newItem.description
        }
    ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryLogViewHolder =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.time_entries_log_item, parent, false)
                .let(::TimeEntryLogViewHolder)

        override fun onBindViewHolder(holder: TimeEntryLogViewHolder, position: Int) {
            holder.bind(getItem(position) as FlatTimeEntryItem)
        }
    }

    class TimeEntryLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description = itemView.findViewById<TextView>(R.id.description)

        fun bind(item: FlatTimeEntryItem) {
            description.text = item.description
        }
    }

    private val disposeBag = CompositeDisposable()
    @Inject
    internal lateinit var store : Store<TimeEntriesLogState, TimeEntriesLogAction>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity().applicationContext as TimerComponentProvider)
            .provideTimerComponent().inject(this)

        startTimeEntryButton.setOnClickListener {
            store.dispatch(TimeEntriesLogAction.StartTimeEntryButtonTapped)
        }

        recyclerView.adapter = adapter

        store.state
            .map { it.items }
            .subscribe(adapter::submitList)
            .addTo(disposeBag)
    }
}
