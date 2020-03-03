package com.toggl.architecture

sealed class Loadable<Value> {
    class Nothing<Value> : Loadable<Value>()
    class Loading<Value> : Loadable<Value>()
    class Error<Value>(val failure: Failure) : Loadable<Value>()
    class Loaded<Value>(val value: Value) : Loadable<Value>()
}

fun <T> Loadable<T>.isLoaded() = this is Loadable.Loaded<T>