package com.example.myapplication.data.repositories.local

import androidx.paging.DataSource
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ILocalRepository {

    fun getAllUsersPerPage(): DataSource.Factory<Int, UserEntity>

    fun getAllUsers(): Flowable<List<UserEntity>>

    fun saveAllUsers(users: List<UserEntity>): Completable

    fun updateUserColor(id: String, color: Int): Completable

    fun getAllUsersWithLimit(count: Int): List<UserEntity>

    fun getAllUsersAfterWithLimit(userKey: String, count: Int): List<UserEntity>

}