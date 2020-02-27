package com.toggl.architecture.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.toggl.architecture.R
import com.toggl.architecture.extensions.TogglEpoxyController

abstract class BaseEpoxyFragment<V : ViewDataBinding>  : BaseMvRxFragment() {

    protected val epoxyController by lazy { epoxyController() }

    var binding: V? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createBinding(inflater, container, savedInstanceState)
            .also { binding = it }
            .also { epoxyRecyclerView(it).setController(epoxyController) }
            .root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(requireBinding(), savedInstanceState)
        epoxyRecyclerView(requireBinding()).apply {
            adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    val firstVisiblePosition = (this@apply.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()
                    if (positionStart == 0 && firstVisiblePosition == 0) {
                        scrollToPosition(0)
                    }
                }
            })
        }
    }

    abstract fun onViewCreated(binding: V, savedInstanceState: Bundle?)

    protected fun requireBinding(): V = requireNotNull(binding)

    final override fun invalidate() {
        invalidate(requireBinding())
        epoxyRecyclerView(requireBinding()).requestModelBuild()
    }

    protected abstract fun invalidate(binding: V)

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): V

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    abstract fun epoxyController(): TogglEpoxyController

    abstract fun epoxyRecyclerView(binding: V): EpoxyRecyclerView
}