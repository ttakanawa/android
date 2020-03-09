package com.toggl.architecture

sealed class Loadable<out Value> {
    open operator fun invoke(): Value? = null

    object Uninitialized : Loadable<Nothing>()
    class Loading<out Value> : Loadable<Value>()
    class Error<out Value>(val failure: Failure) : Loadable<Value>()
    class Loaded<out Value>(val value: Value) : Loadable<Value>() {
        override operator fun invoke(): Value = value
    }
}

fun <T> Loadable<T>.isLoaded() = this is Loadable.Loaded<T>
