package com.toggl.architecture.extensions

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState
import com.toggl.architecture.core.StoreViewModel
import com.toggl.architecture.ui.BaseEpoxyFragment

class TogglEpoxyController(
    val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {
    override fun buildModels() {
        buildModelsCallback()
    }
}

/**
 * Create a [MvRxEpoxyController] that builds models with the given callback.
 */
fun BaseEpoxyFragment<*>.simpleController(
    buildModels: EpoxyController.() -> Unit
) = TogglEpoxyController {
    // Models are built asynchronously, so it is possible that this is called after the fragment
    // is detached under certain race conditions.
    if (view == null || isRemoving) return@TogglEpoxyController
    buildModels()
}

/**
 * Create a [TogglEpoxyController] that builds models with the given callback.
 * When models are built the current state of the viewmodel will be provided.
 */
fun <Action, S : MvRxState, A : StoreViewModel<Action ,S>> BaseEpoxyFragment<*>.simpleController(
    viewModel: A,
    buildModels: EpoxyController.(state: S) -> Unit
) = TogglEpoxyController {
    if (view == null || isRemoving) return@TogglEpoxyController
    withState(viewModel) { state ->
        buildModels(state)
    }
}

fun <A : BaseMvRxViewModel<B>, B : MvRxState, C : BaseMvRxViewModel<D>, D : MvRxState> BaseEpoxyFragment<*>.simpleController(
    viewModel1: A,
    viewModel2: C,
    buildModels: EpoxyController.(state1: B, state2: D) -> Unit
) = TogglEpoxyController {
    if (view == null || isRemoving) return@TogglEpoxyController
    withState(viewModel1, viewModel2) { state1, state2 ->
        buildModels(state1, state2)
    }
}