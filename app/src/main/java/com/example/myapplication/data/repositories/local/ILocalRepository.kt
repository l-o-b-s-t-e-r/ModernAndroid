package com.example.myapplication.data.repositories.local

import androidx.lifecycle.LiveData
import com.example.myapplication.domain.entities.User
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalRepository {

    fun getAllUsers(): LiveData<List<User>>

    fun saveAllUsers(users: List<User>): Completable

    fun updateUserColor(id: String, color: Int): Completable

}