package com.toggl.architecture.core

import androidx.fragment.app.FragmentActivity

abstract class Coordinator {
    abstract fun start(activity: FragmentActivity)
}