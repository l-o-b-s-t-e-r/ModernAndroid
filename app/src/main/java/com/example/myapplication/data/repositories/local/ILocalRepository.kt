package com.example.myapplication.data.repositories.local

import com.example.myapplication.domain.entities.User
import io.reactivex.Completable
import io.reactivex.Flowable

interface ILocalRepository {

    fun getAllUsers(): Flowable<List<User>>

    fun saveAllUsers(users: List<User>): Completable

    fun updateUserColor(id: String, color: Int): Completable

}