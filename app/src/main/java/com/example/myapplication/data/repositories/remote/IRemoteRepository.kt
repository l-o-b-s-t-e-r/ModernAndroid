package com.example.myapplication.data.repositories.remote

import com.example.myapplication.domain.dto.UserDto

interface IRemoteRepository {

    fun getAllUsersWithLimit(count: Int): List<UserDto>

    fun getAllUsersWithLimit(query: String, count: Int): List<UserDto>

    fun getAllUsersAfterWithLimit(query: String, userKey: String, count: Int): List<UserDto>

}