package com.example.myapplication.domain

import com.example.myapplication.domain.dto.UserDto
import io.reactivex.Observable
import javax.inject.Inject

class ItemClickFilter @Inject constructor(private val eventPublisher: EventPublisher) {

    fun filter(): Observable<UserDto> {
        return eventPublisher.observableEvent.filter {
            it.type == EventType.ITEM_CLICK && it.obj is UserDto
        }.map {
            it.obj as UserDto
        }
    }

}