package com.toggl.models.common

enum class SwipeDirection {
    Left, Right;

    override fun toString(): String =
        when (this) {
            Left -> "left"
            Right -> "right"
        }
}