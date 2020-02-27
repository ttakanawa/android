package com.toggl.timer.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toggl.architecture.core.Store
import com.toggl.timer.R
import com.toggl.timer.di.TimerComponentProvider
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.states.FlatTimeEntryItem
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimeEntryLogViewModel
import kotlinx.android.synthetic.main.time_entries_log_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class TimeEntriesLogFragment : Fragment(R.layout.time_entries_log_fragment) {

    private val adapter = TimeEntryLogAdapter { store.dispatch(TimeEntriesLogAction.ContinueButtonTapped(it)) }

    class TimeEntryLogAdapter(val onContinueTappedListener: (Long) -> Unit = {}) : ListAdapter<TimeEntryLogViewModel, TimeEntryLogAdapter.TimeEntryLogViewHolder>(
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

        inner class TimeEntryLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val description = itemView.findViewById<TextView>(R.id.description)
            private val duration = itemView.findViewById<TextView>(R.id.duration)
            private val continueButton = itemView.findViewById<View>(R.id.continueButton)

            fun bind(item: FlatTimeEntryItem) {
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
    }

    @Inject
    internal lateinit var store : Store<TimeEntriesLogState, TimeEntriesLogAction>

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as TimerComponentProvider)
            .provideTimerComponent().inject(this)
        super.onAttach(context)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter

        timeEntryDescriptionEditText.doOnTextChanged { text, _, _, _ ->
            val action = TimeEntriesLogAction.TimeEntryDescriptionChanged(text.toString())
            store.dispatch(action)
        }


        lifecycleScope.launch(Dispatchers.Main) {

            store.state
                .map { it.editedDescription }
                .distinctUntilChanged()
                .filter { timeEntryDescriptionEditText.text.toString() != it }
                .onEach { timeEntryDescriptionEditText.setText(it) }
                .launchIn(this)

            store.state
                .map { it.runningTimeEntry != null }
                .distinctUntilChanged()
                .onEach { setEditedTimeEntryState(it) }
                .launchIn(this)

            store.state
                .map { it.items }
                .onEach { adapter.submitList(it) }
                .launchIn(this)
        }
    }

    private fun setEditedTimeEntryState(isRunningTimeEntry: Boolean) {
        if (isRunningTimeEntry) {
            startTimeEntryButton.setImageResource(R.drawable.ic_stop)
            startTimeEntryButton.setOnClickListener {
                store.dispatch(TimeEntriesLogAction.StopTimeEntryButtonTapped)
            }
            timeEntryDescriptionInputLayout.visibility = View.GONE
            runningTimeEntryLayout.visibility = View.VISIBLE
        } else {
            startTimeEntryButton.setImageResource(R.drawable.ic_play_big)
            startTimeEntryButton.setOnClickListener {
                store.dispatch(TimeEntriesLogAction.StartTimeEntryButtonTapped)
            }
            timeEntryDescriptionInputLayout.visibility = View.VISIBLE
            runningTimeEntryLayout.visibility = View.GONE
        }
    }
}