
package com.toggl.timer.log.ui

import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.toggl.timer.R
import com.toggl.timer.log.domain.FlatTimeEntryItem
import java.util.concurrent.TimeUnit

class TimeEntryLogViewHolder(
    itemView: View,
    private val onContinueTappedListener: (Long) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val addDescriptionLabel = itemView.findViewById<View>(R.id.add_description_label)
    private val project = itemView.findViewById<TextView>(R.id.project_label)
    private val description = itemView.findViewById<TextView>(R.id.description)
    private val duration = itemView.findViewById<TextView>(R.id.duration)
    private val continueButton = itemView.findViewById<View>(R.id.continue_button)

    fun bind(item: FlatTimeEntryItem) {

        val hasDescription = item.description.isNotBlank()
        addDescriptionLabel.isVisible = !hasDescription
        description.isVisible = hasDescription
        description.text = item.description

        if (item.project == null) {
            project.isVisible = false
        } else {
            project.isVisible = true
            project.text = item.project.name
            project.setTextColor(item.project.color.toColorInt())
        }

        item.duration?.let { durationMillis ->
            val hms = String.format(
                "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(durationMillis),
                TimeUnit.MILLISECONDS.toMinutes(durationMillis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(durationMillis) % TimeUnit.MINUTES.toSeconds(1)
            )
            duration.text = hms
        }

        continueButton.setOnClickListener {
            onContinueTappedListener(item.id)
        }
    }
}
