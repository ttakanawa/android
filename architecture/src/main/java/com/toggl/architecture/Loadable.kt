package com.toggl.architecture

sealed class Loadable<Value> {
    class Nothing<Value> : Loadable<Value>()
    class Loading<Value> : Loadable<Value>()
    class Error<Value>(val throwable: Throwable) : Loadable<Value>()
    class Loaded<Value>(value: Value) : Loadable<Value>()
}
