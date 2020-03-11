package com.toggl.timer.log.domain

import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.createTimeEntry
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import io.mockk.mockk

class ContinueButtonTappedActionTests : FreeSpec({

    val repository = mockk<TimeEntryRepository>()
    val reducer = createTimeEntriesLogReducer(repository)
    val testTe = createTimeEntry(1, "test")

    "The ContinueButtonTapped action" - {
        "should throw when there are no time entries" - {
            "with the matching id" {
                val initialState = createEmptyState().copy(timeEntries = listOf(testTe))
                var state = initialState
                val settableValue = state.toSettableValue { state = it }

                shouldThrow<IllegalStateException> {
                    reducer.reduce(settableValue, TimeEntriesLogAction.ContinueButtonTapped(2))
                }
            }

            "at all" {
                val initialState = createEmptyState()

                assertAll(fn = { id: Long ->
                    var state = initialState
                    val settableValue = state.toSettableValue { state = it }
                    shouldThrow<IllegalStateException> {
                        reducer.reduce(settableValue, TimeEntriesLogAction.ContinueButtonTapped(id))
                    }
                })
            }
        }
    }
})