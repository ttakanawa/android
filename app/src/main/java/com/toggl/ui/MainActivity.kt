package com.toggl.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.toggl.R
import com.toggl.TogglApplication
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Store
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var store: Store<AppState, AppAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        (applicationContext as TogglApplication).appComponent.inject(this)

        store.dispatch(AppAction.Load)
    }
}
