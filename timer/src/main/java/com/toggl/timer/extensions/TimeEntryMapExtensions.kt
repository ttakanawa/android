package com.toggl.timer.extensions

import com.toggl.models.domain.Project
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.log.domain.FlatTimeEntryItem
import com.toggl.timer.log.domain.ProjectViewModel

fun Map<Long, TimeEntry>.replaceTimeEntryWithId(id: Long, timeEntryToReplace: TimeEntry): Map<Long, TimeEntry> =
    toMutableMap()
        .also { it[id] = timeEntryToReplace }
        .toMap()

fun Map<Long, TimeEntry>.runningTimeEntryOrNull() =
    this.values.firstOrNull { it.duration == null }

fun Map<Long, TimeEntry>.toTimeEntryViewModelList(projects: Map<Long, Project>) =
    values.filter { it.duration != null }
        .map { timeEntry ->
            val projectId = timeEntry.projectId
            val project =
                if (projectId == null) null
                else projects[projectId]?.run { ProjectViewModel(id, name, color) }

            FlatTimeEntryItem(
                id = timeEntry.id,
                description = timeEntry.description,
                startTime = timeEntry.startTime,
                duration = timeEntry.duration,
                project = project,
                billable = timeEntry.billable
            )
        }
