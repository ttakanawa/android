package com.toggl.timer.log.domain

import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.createTimeEntry
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import io.mockk.mockk

class TimeEntryGroupTappedActionTests : FreeSpec({

    val repository = mockk<TimeEntryRepository>()
    val reducer = createTimeEntriesLogReducer(repository)
    val testTe = createTimeEntry(1, "test")

    "The TimeEntryTapped action" - {
        "should thrown when there are no time entries" - {
            "with the matching id" {
                val initialState = createEmptyState(listOf(testTe))
                var state = initialState
                val settableValue = state.toSettableValue { state = it }
                shouldThrow<IllegalStateException> {
                    reducer.reduce(settableValue, TimeEntriesLogAction.TimeEntryTapped(2))
                }
            }

            "at all" {
                val initialState = createEmptyState()
                assertAll(fn = { id: Long ->
                    var state = initialState
                    val settableValue = state.toSettableValue { state = it }
                    shouldThrow<IllegalStateException> {
                        reducer.reduce(settableValue, TimeEntriesLogAction.TimeEntryTapped(id))
                    }
                })
            }
        }

        "set the editing time entry property when the time entry exists" {
            val initialState = createEmptyState(listOf(testTe))

            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, TimeEntriesLogAction.TimeEntryTapped(1))
            state.editedTimeEntry shouldBe testTe
        }
    }
})
