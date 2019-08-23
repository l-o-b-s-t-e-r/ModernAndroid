package com.example.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.domain.entities.User
import io.reactivex.Completable

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAll(users: List<User>): Completable

    @Query("UPDATE user SET color = :color WHERE id = :id")
    fun updateColor(id: String, color: Int): Completable

}