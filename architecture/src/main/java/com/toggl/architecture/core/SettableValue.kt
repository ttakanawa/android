package com.toggl.architecture.core

class SettableValue<T>(private val getValue: () -> T, private val setValue: (T) -> Unit) {
    var value: T
        get() = getValue()
        set(value) = setValue(value)

    fun <R> map(getMap: (T) -> R, mapSet: (T, R) -> T) : SettableValue<R> =
        SettableValue(
            getValue = { getMap(this.value) },
            setValue = { value -> this.value = mapSet(this.value, value) }
        )
}