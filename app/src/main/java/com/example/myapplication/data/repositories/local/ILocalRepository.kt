package com.example.myapplication.data.repositories.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.myapplication.domain.NetworkState
import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ILocalRepository {

    fun getAllUsersPerPage(): DataSource.Factory<Int, UserEntity>

    fun getAllUsers(): Flowable<List<UserEntity>>

    fun saveAllUsers(users: List<UserEntity>)

    fun updateAllUsers(users: List<UserEntity>)

    fun updateUserColor(id: String, color: Int): Completable

    fun getAllUsersWithLimit(count: Int): List<UserEntity>

    fun getAllUsersAfterWithLimit(userKey: String, count: Int): List<UserEntity>

    fun getAllUsersPerPage(config: PagedList.Config, networkState: MutableLiveData<NetworkState>): LiveData<PagedList<UserDto>>

    fun deleteUserById(id: String): Completable

    fun deleteFirstUser(): Completable

    fun showLastUser(): Completable

    fun hideFirstUser(): Completable
}