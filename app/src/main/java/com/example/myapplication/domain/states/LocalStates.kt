package com.example.myapplication.domain.states

import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LocalStates @Inject constructor() {

    val usersState = PublishSubject.create<UsersState>()

}