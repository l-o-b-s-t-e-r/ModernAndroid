package com.example.myapplication.data.repositories.remote

import com.example.myapplication.domain.entities.User
import io.reactivex.Single

interface IRemoteRepository {

    fun loadAllUsers(): Single<List<User>>

}