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

    @Query("SELECT * FROM users where is_visible = 1 AND name LIKE :query ORDER BY name ASC")
    fun getAllUsersByQuery(query: String): DataSource.Factory<Int, UserEntity>

    @Query("SELECT * FROM users WHERE is_visible = 1 ORDER BY name ASC")
    fun getAllUsersSortedByName(): DataSource.Factory<Int, UserEntity>

    @Query("SELECT * FROM users limit 0")
    fun getNothing(): DataSource.Factory<Int, UserEntity>

    @Query("SELECT * FROM users")
    fun getAll(): Flowable<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAll(users: List<UserEntity>)

    @Query("UPDATE users SET color = :color WHERE id = :id")
    fun updateColor(id: String, color: Int): Completable

    @Query("SELECT * FROM users WHERE is_visible = 1 ORDER BY name ASC limit :count")
    fun getAllWithLimit(count: Int): List<UserEntity>

    @Query("SELECT * FROM users WHERE name > :userKey AND is_visible = 1 ORDER BY name ASC limit :count")
    fun getAfterWithLimit(userKey: String, count: Int): List<UserEntity>

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteById(id: String): Completable

    @Query("DELETE FROM users WHERE id = (SELECT id FROM users WHERE is_visible = 'true' ORDER BY name ASC limit 1)")
    fun deleteFirst(): Completable

    @Query("DELETE FROM users")
    fun deleteAll()

    @Query("UPDATE users SET is_visible = 0 WHERE id = (SELECT id FROM users WHERE is_visible = 1 AND name LIKE :query ORDER BY name ASC limit 1)")
    fun hideFirst(query: String): Completable

    @Query("UPDATE users SET is_visible = 1 WHERE id = (SELECT id FROM users WHERE is_visible = 0 AND name LIKE :query ORDER BY name DESC limit 1)")
    fun showLast(query: String): Completable
}