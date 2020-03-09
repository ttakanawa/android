package com.toggl.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
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

    private fun changeTab(menuItem: MenuItem): Boolean {
        nav_host_fragment.findNavController().navigate(
            when (menuItem.itemId) {
                R.id.timer_tab -> DeepLinkUrls.timeEntriesLog
                R.id.reports_tab -> DeepLinkUrls.reports
                R.id.calendar_tab -> DeepLinkUrls.calendar
                else -> throw NotImplementedError()
            }
        )

        return true
    }

    private fun scrollUpOnTab(menuItem: MenuItem) {
        Log.d("MainFragment", menuItem.title.toString())
    }
}
