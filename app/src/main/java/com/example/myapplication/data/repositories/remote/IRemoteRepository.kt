package com.example.myapplication.data.repositories.remote

import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Single

interface IRemoteRepository {

    fun loadAllUsers(): Single<List<UserEntity>>

}