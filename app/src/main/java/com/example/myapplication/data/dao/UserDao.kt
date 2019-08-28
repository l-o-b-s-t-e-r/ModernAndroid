package com.example.myapplication.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllPerPage(): DataSource.Factory<Int, UserEntity>

    @Query("SELECT * FROM users")
    fun getAll(): Flowable<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAll(users: List<UserEntity>): Completable

    @Query("UPDATE users SET color = :color WHERE id = :id")
    fun updateColor(id: String, color: Int): Completable

    @Query("SELECT * FROM users ORDER BY name ASC limit :count")
    fun getAllWithLimit(count: Int): List<UserEntity>

    @Query("SELECT * FROM users WHERE name > :userKey ORDER BY name ASC limit :count")
    fun getAfterWithLimit(userKey: String, count: Int): List<UserEntity>

}