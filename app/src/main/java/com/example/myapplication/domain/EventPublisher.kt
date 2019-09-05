package com.example.myapplication.domain

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventPublisher @Inject constructor() {

    private var eventEmitter: ObservableEmitter<Event>? = null

    val observableEvent = Observable.create<Event> {
        eventEmitter = it
    }.share() //publish()

    fun send(event: Event) {
        eventEmitter!!.onNext(event)
    }
}