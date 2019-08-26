package com.example.myapplication.data.repositories.local

import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.domain.entities.User
import io.reactivex.Completable
import io.reactivex.Flowable

class LocalRepository(private val db: AppDatabase) : ILocalRepository {

    override fun saveAllUsers(users: List<User>): Completable {
        return db.userDao().saveAll(users)
    }

    override fun getAllUsers(): Flowable<List<User>> {
        return db.userDao().getAll()
    }

    override fun updateUserColor(id: String, color: Int): Completable {
        return db.userDao().updateColor(id, color)
    }
}