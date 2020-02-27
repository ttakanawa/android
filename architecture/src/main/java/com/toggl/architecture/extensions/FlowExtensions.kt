package com.toggl.architecture.extensions

import com.toggl.architecture.isLoaded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun <T> Flow<T>.merge(otherFlow: Flow<T>) : Flow<T> = flow {
    emitAll(this@merge)
    emitAll(otherFlow)
}

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun <T> List<Flow<T>>.mergeAll(): Flow<T> =
    reduce { acc, otherFlow -> acc.merge(otherFlow) }

@FlowPreview
fun <T> Flow<T>.emitIf(predicate: (T) -> Boolean) : Flow<Unit> =
    flatMapConcat { if (predicate(it)) flowOf(Unit) else emptyFlow() }