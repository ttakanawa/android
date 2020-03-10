package com.toggl.timer.log.domain

import com.toggl.architecture.core.noEffect
import com.toggl.models.common.SwipeDirection
import com.toggl.repository.timeentry.StartTimeEntryResult
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.createTimeEntry
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.single

class TimeEntryStartedActionTests : FreeSpec({

    val repository = mockk<TimeEntryRepository>()
    val reducer = createTimeEntriesLogReducer(repository)

    "The TimeEntryStarted action" - {

        "updates the state to add the time entry" - {
            var state = createEmptyState()
            val settableValue = state.toSettableValue { state = it }

            assertAll(fn = { id: Long ->
                val timeEntry = createTimeEntry(id, "test")
                val action = TimeEntriesLogAction.TimeEntryStarted(timeEntry, null)
                val effect = reducer.reduce(settableValue, action)
                effect shouldBe noEffect()
                state.timeEntries shouldContain timeEntry
            })
        }
    }
})