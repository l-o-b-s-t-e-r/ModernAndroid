package com.example.myapplication.domain.states

import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class GlobalStates @Inject constructor() {

    val refreshState = PublishSubject.create<RefreshState>()

}