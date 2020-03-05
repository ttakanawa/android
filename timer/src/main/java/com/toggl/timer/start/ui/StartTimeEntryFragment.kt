package com.toggl.timer.start.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.R
import com.toggl.timer.di.TimerComponentProvider
import com.toggl.timer.extensions.runningTimeEntryOrNull
import com.toggl.timer.start.domain.StartTimeEntryAction
import kotlinx.android.synthetic.main.start_time_entry_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartTimeEntryFragment : Fragment(R.layout.start_time_entry_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val store : StartTimeEntryStoreViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as TimerComponentProvider)
            .provideTimerComponent().inject(this)
        super.onAttach(context)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        time_entry_description.doOnTextChanged { text, _, _, _ ->
            val action = StartTimeEntryAction.TimeEntryDescriptionChanged(text.toString())
            store.dispatch(action)
        }

        lifecycleScope.launch {

            store.state
                .map { it.editedDescription }
                .distinctUntilChanged()
                .onEach {
                    if (time_entry_description?.text.toString() != it ) {
                        time_entry_description.setText(it) }
                }
                .launchIn(this)

            val runningTimeEntryFlow = store.state
                .map { it.timeEntries.runningTimeEntryOrNull() }
                .distinctUntilChanged()

            runningTimeEntryFlow
                .filterNotNull()
                .onEach { updateRunningTimeEntryCard(it) }
                .launchIn(this)

            runningTimeEntryFlow
                .map { it != null }
                .distinctUntilChanged()
                .onEach { setEditedTimeEntryState(it) }
                .launchIn(this)
        }
    }

    private fun updateRunningTimeEntryCard(timeEntry: TimeEntry) {
        runningTimeEntryDescription.text = timeEntry.description
    }

    private fun setEditedTimeEntryState(timeEntryIsRunning: Boolean) {
        time_entry_description_input_layout.isVisible = !timeEntryIsRunning
        running_time_entry_layout.isVisible = timeEntryIsRunning

        with(start_time_entry_button) {
            if (timeEntryIsRunning) {
                val color = ContextCompat.getColor(
                    requireContext(),
                    R.color.stop_time_entry_button_background
                )
                backgroundTintList = ColorStateList.valueOf(color)
                setImageResource(R.drawable.ic_stop)
                setOnClickListener {
                    store.dispatch(StartTimeEntryAction.StopTimeEntryButtonTapped)
                }
            } else {
                val color = ContextCompat.getColor(
                    requireContext(),
                    R.color.start_time_entry_button_background
                )
                backgroundTintList = ColorStateList.valueOf(color)
                setImageResource(R.drawable.ic_play_big)
                setOnClickListener {
                    store.dispatch(StartTimeEntryAction.StartTimeEntryButtonTapped)
                }
            }
        }
    }
}