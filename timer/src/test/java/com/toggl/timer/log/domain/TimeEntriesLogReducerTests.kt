/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2020, Toggl
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * © 2020 GitHub, Inc.
 */
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
