package com.toggl.timer.log.domain

import com.toggl.architecture.core.noEffect
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.createTimeEntry
import io.kotlintest.matchers.collections.shouldNotContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import io.mockk.mockk

class TimeEntryDeletedActionTests: FreeSpec({

    val repository = mockk<TimeEntryRepository>()
    val entriesInDatabase = (1L..3L).map { createTimeEntry(it, "test") }
    val reducer = createTimeEntriesLogReducer(repository)

    "The TimeEntryDeleted action" - {

        "updates the state to remove the time entry from the time entry list" - {
            var state = createEmptyState().copy(timeEntries = entriesInDatabase)
            val settableValue = state.toSettableValue { state = it }

            val timeEntry = entriesInDatabase.first()
            val action = TimeEntriesLogAction.TimeEntryDeleted(timeEntry)
            val effect = reducer.reduce(settableValue, action)
            effect shouldBe noEffect()
            state.timeEntries shouldNotContain timeEntry
        }
    }
})