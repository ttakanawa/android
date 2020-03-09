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
package com.toggl.timer.log.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.toggl.timer.R
import com.toggl.timer.log.domain.FlatTimeEntryItem
import com.toggl.timer.log.domain.TimeEntryViewModel

class TimeEntryLogAdapter(private val onContinueTappedListener: (Long) -> Unit = {}) :
    ListAdapter<TimeEntryViewModel, TimeEntryLogViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeEntryLogViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.time_entries_log_item, parent, false)
            .let { TimeEntryLogViewHolder(it, onContinueTappedListener) }

    override fun onBindViewHolder(holder: TimeEntryLogViewHolder, position: Int) {
        holder.bind(getItem(position) as FlatTimeEntryItem)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<TimeEntryViewModel>() {
            override fun areItemsTheSame(
                oldItem: TimeEntryViewModel,
                newItem: TimeEntryViewModel
            ): Boolean =
                oldItem is FlatTimeEntryItem &&
                        newItem is FlatTimeEntryItem &&
                        oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TimeEntryViewModel,
                newItem: TimeEntryViewModel
            ): Boolean =
                oldItem is FlatTimeEntryItem &&
                newItem is FlatTimeEntryItem &&
                oldItem.description == newItem.description
        }
    }
}
