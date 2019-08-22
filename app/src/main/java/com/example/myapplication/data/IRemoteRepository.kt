package com.example.myapplication.data

import com.example.myapplication.domain.entities.User
import io.reactivex.Single

interface IRemoteRepository {

    fun loadAllUsers(): Single<List<User>>

}