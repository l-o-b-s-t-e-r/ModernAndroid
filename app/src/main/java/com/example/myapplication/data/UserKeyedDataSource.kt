package com.example.myapplication.data

import androidx.paging.ItemKeyedDataSource
import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.entities.UserEntity
import javax.inject.Inject

class UserKeyedDataSource @Inject constructor(private val localRepository: ILocalRepository) : ItemKeyedDataSource<String, UserEntity>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<UserEntity>) {
        callback.onResult(localRepository.getAllUsersWithLimit(params.requestedLoadSize))
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<UserEntity>) {
        callback.onResult(localRepository.getAllUsersAfterWithLimit(params.key, params.requestedLoadSize))
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<UserEntity>) {

    }

    override fun getKey(item: UserEntity) = item.name

}