package com.example.myapplication.data.repositories.local

import androidx.paging.DataSource
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

class LocalRepository(private val db: AppDatabase) : ILocalRepository {

    override fun getAllUsersPerPage(): DataSource.Factory<Int, UserEntity> {
        return db.userDao().getAllPerPage()
    }

    override fun saveAllUsers(users: List<UserEntity>): Completable {
        return db.userDao().saveAll(users)
    }

    override fun getAllUsers(): Flowable<List<UserEntity>> {
        return db.userDao().getAll()
    }

    override fun updateUserColor(id: String, color: Int): Completable {
        return db.userDao().updateColor(id, color)
    }

    override fun getAllUsersWithLimit(count: Int): List<UserEntity> {
        return db.userDao().getAllWithLimit(count)
    }

    override fun getAllUsersAfterWithLimit(userKey: String, count: Int): List<UserEntity> {
        return db.userDao().getAfterWithLimit(userKey, count)
    }
}