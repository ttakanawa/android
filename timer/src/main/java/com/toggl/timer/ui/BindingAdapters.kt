package com.toggl.timer.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.concurrent.TimeUnit

// these can be moved to some common scope

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:durationText")
fun TextView.durationText(durationMillis: Long?) {
    val hms = durationMillis?.let {
        String.format(
            "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(durationMillis),
            TimeUnit.MILLISECONDS.toMinutes(durationMillis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(durationMillis) % TimeUnit.MINUTES.toSeconds(1)
        )
    } ?: ""
    text = hms
}