package com.example.myapplication.data.repositories.remote

import com.example.myapplication.domain.entities.UserEntity

interface IRemoteRepository {

    fun getAllUsersWithLimit(count: Int): List<UserEntity>

    fun getAllUsersAfterWithLimit(userKey: String, count: Int): List<UserEntity>

}