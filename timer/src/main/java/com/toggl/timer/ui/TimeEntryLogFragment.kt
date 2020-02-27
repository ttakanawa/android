package com.toggl.timer.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.toggl.architecture.extensions.simpleController
import com.toggl.architecture.ui.BaseEpoxyFragment
import com.toggl.timer.R
import com.toggl.timer.databinding.TimeEntriesLogFragmentBinding
import com.toggl.timer.di.TimerComponentProvider
import com.toggl.timer.domain.*
import com.toggl.timer.feedbackItem
import com.toggl.timer.timeEntryLogItem
import javax.inject.Inject


class TimeEntryLogFragment : BaseEpoxyFragment<TimeEntriesLogFragmentBinding>() {

    @Inject internal lateinit var loginStoreFactory: TimeEntryLogStore.Factory

    private val store: TimeEntryLogStore by fragmentViewModel()

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as TimerComponentProvider)
            .provideTimerComponent().inject(this)
        super.onAttach(context)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): TimeEntriesLogFragmentBinding {
        return TimeEntriesLogFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(
        binding: TimeEntriesLogFragmentBinding,
        savedInstanceState: Bundle?
    ) {
        // This could be moved to layout as well but I think is better to have these in one place
        binding.startTimeEntryButton.setOnClickListener {
            store.dispatch(TimeEntryLogAction.StartTimeEntryButtonTapped)
        }
        binding.stopTimeEntryButton.setOnClickListener {
            store.dispatch(TimeEntryLogAction.StopTimeEntryButtonTapped)
        }
        binding.bottomSheet.timeEntryDescriptionEditText.addTextChangedListener { text ->
            store.dispatch(TimeEntryLogAction.TimeEntryDescriptionChanged(text.toString()))
        }
    }

    override fun invalidate(binding: TimeEntriesLogFragmentBinding) = withState(store) { state ->
        binding.state = state
    }

    override fun epoxyController() = simpleController(store) { state ->
        for (item in state.items.reversed()) {
            when(item) {
                is FlatTimeEntryItem -> timeEntryLogItem {
                    id(item.id)
                    description(item.description)
                    duration(item.duration)
                    onContinue { _ -> store.dispatch(TimeEntryLogAction.ContinueButtonTapped(item.description)) }
                }
                FeedbackItem -> feedbackItem {
                    id("feedback")
                }
            }
        }
    }

    override fun epoxyRecyclerView(binding: TimeEntriesLogFragmentBinding) = binding.epoxyRecyclerView
}