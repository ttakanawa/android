package com.toggl.architecture

sealed class Loadable<Value> {
    class Nothing<Value> : Loadable<Value>()
    class Loading<Value> : Loadable<Value>()
    class Error<Value>(val throwable: Failure) : Loadable<Value>()
    class Loaded<Value>(val value: Value) : Loadable<Value>()
}
