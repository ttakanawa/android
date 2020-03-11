package com.toggl.models.common

sealed class SwipeDirection {
    object Left : SwipeDirection()
    object Right : SwipeDirection()

    override fun toString(): String =
        when (this) {
            Left -> "left"
            Right -> "right"
        }
}