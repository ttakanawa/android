package com.toggl.architecture.core

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single

class Effect<Action> constructor(private val observable: Observable<Action>) : Observable<Action>() {

    companion object {
        fun <Action> empty() =
            Effect<Action>(Observable.empty())

        fun <Action> from(effects: List<Effect<Action>>) =
            Effect(effects.reduce { acc: Observable<Action>, effect -> acc.mergeWith(effect) })
    }

    override fun subscribeActual(observer: Observer<in Action>?) {
        observable.subscribe(observer)
    }
}

fun <T> Single<T>.toEffect() =
    Effect(this.toObservable())

fun <T> Observable<T>.toEffect() =
    Effect(this)