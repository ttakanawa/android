package com.toggl.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.toggl.R
import com.toggl.common.DeepLinkUrls
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(R.layout.main_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottom_navigation.setOnNavigationItemSelectedListener(::changeTab)
        bottom_navigation.setOnNavigationItemReselectedListener(::scrollUpOnTab)
    }

    private fun changeTab(menuItem: MenuItem) : Boolean {
        nav_host_fragment.findNavController().navigate(
            when(menuItem.itemId) {
                R.id.timer_tab -> DeepLinkUrls.timeEntriesLog
                R.id.reports_tab -> DeepLinkUrls.reports
                R.id.calendar_tab -> DeepLinkUrls.calendar
                else -> throw NotImplementedError()
            }
        )

        return true
    }

    private fun scrollUpOnTab(menuItem: MenuItem) {
    }
}

class ReportsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        View(context).apply {
            background = ColorDrawable(Color.parseColor("#FF00FF"))
        }
}

class CalendarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        View(context).apply {
            background = ColorDrawable(Color.parseColor("#FFFF00"))
        }
}