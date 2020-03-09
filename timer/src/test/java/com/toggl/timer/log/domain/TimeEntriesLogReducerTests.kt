
package com.toggl.timer.log.domain

import com.toggl.architecture.core.SettableValue
import com.toggl.architecture.core.noEffect
import com.toggl.models.domain.TimeEntry
import com.toggl.repository.timeentry.TimeEntryRepository
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.mockk
import java.util.Date

fun TimeEntriesLogState.toSettableValue(setFunction: (TimeEntriesLogState) -> Unit) =
    SettableValue({ this }, setFunction)

class WhenReceivingContinueButtonTapped : StringSpec() {
    init {
        val repository = mockk<TimeEntryRepository>()
        val reducer = createTimeEntriesLogReducer(repository)
        val testTe = TimeEntry(
            1,
            "test",
            Date(),
            null,
            false,
            null,
            null
        )
        "ContinueButtonTapped with no-existing te id shouldn't have any effect" {
            val initialState = TimeEntriesLogState(listOf(testTe), mapOf())
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            val effect = reducer.reduce(settableValue, TimeEntriesLogAction.ContinueButtonTapped(2))
            effect shouldBe noEffect()
            state shouldBe initialState
        }

        "If there are no TEs no matter what you pass in ContinueButtonTapped the reducer shouldn't do anything" {
            val initialState = TimeEntriesLogState(listOf(), mapOf())

            assertAll(fn = { id: Long ->
                var state = initialState
                val settableValue = state.toSettableValue { state = it }
                val effect = reducer.reduce(settableValue, TimeEntriesLogAction.ContinueButtonTapped(id))
                effect shouldBe noEffect()
                state shouldBe initialState
            })
        }
    }
}
