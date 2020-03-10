package com.toggl.timer.log.domain

import com.toggl.architecture.core.noEffect
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.createTimeEntry
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import io.mockk.mockk

class ContinueButtonTappedActionTests : WordSpec({

    val repository = mockk<TimeEntryRepository>()
    val reducer = createTimeEntriesLogReducer(repository)
    val testTe = createTimeEntry(1, "test")

    "The ContinueButtonTapped action" should {
        "do nothing when there's no time entry with the matching id" {
            val initialState = createEmptyState().copy(timeEntries = listOf(testTe))
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            val effect = reducer.reduce(settableValue, TimeEntriesLogAction.ContinueButtonTapped(2))
            effect shouldBe noEffect()
            state shouldBe initialState
        }

        "do nothing when there are no time entries at all" {
            val initialState = createEmptyState()

            assertAll(fn = { id: Long ->
                var state = initialState
                val settableValue = state.toSettableValue { state = it }
                val effect =
                    reducer.reduce(settableValue, TimeEntriesLogAction.ContinueButtonTapped(id))
                effect shouldBe noEffect()
                state shouldBe initialState
            })
        }
    }
})


