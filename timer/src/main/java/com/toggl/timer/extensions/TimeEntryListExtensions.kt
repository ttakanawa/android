package com.toggl.timer.extensions

import com.toggl.models.domain.Project
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.log.domain.FlatTimeEntryItem
import com.toggl.timer.log.domain.ProjectViewModel

fun List<TimeEntry>.findEntryWithId(id: Long) : TimeEntry? =
    firstOrNull { it.id == id }

fun List<TimeEntry>.replaceTimeEntryWithId(id: Long, timeEntryToReplace: TimeEntry) =
    map { if (it.id == id) timeEntryToReplace else it }

fun List<TimeEntry>.runningTimeEntryOrNull() =
    firstOrNull { it.duration == null }

fun List<TimeEntry>.toTimeEntryViewModelList(projects: Map<Long, Project>) =
    filter { it.duration != null }
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
