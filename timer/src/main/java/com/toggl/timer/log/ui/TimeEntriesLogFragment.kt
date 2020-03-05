package com.toggl.timer.log.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.toggl.timer.R
import com.toggl.timer.di.TimerComponentProvider
import com.toggl.timer.extensions.toTimeEntryViewModelList
import com.toggl.timer.log.domain.TimeEntriesLogAction
import kotlinx.android.synthetic.main.time_entries_log_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class TimeEntriesLogFragment : Fragment(R.layout.time_entries_log_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val store : TimeEntriesLogStoreViewModel by viewModels { viewModelFactory }
    private val adapter = TimeEntryLogAdapter { store.dispatch(TimeEntriesLogAction.ContinueButtonTapped(it)) }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as TimerComponentProvider)
            .provideTimerComponent().inject(this)
        super.onAttach(context)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.adapter = adapter

        lifecycleScope.launch {
            store.state
                .map { it.timeEntries }
                .distinctUntilChanged()
                .map { it.toTimeEntryViewModelList() }
                .onEach { adapter.submitList(it) }
                .launchIn(this)
        }
    }
}