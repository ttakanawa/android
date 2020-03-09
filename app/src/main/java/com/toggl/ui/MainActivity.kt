package com.toggl.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.toggl.R
import com.toggl.TogglApplication
import com.toggl.architecture.core.Store
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.common.DeepLinkUrls
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    @Inject
    lateinit var store: Store<AppState, AppAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as TogglApplication).appComponent.inject(this)

        store.dispatch(AppAction.Load)

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
    }
}
