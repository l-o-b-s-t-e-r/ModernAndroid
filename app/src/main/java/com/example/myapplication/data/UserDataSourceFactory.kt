package com.example.myapplication.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.entities.UserEntity
import javax.inject.Inject

class UserDataSourceFactory @Inject constructor(private val localRepository: ILocalRepository) :
    DataSource.Factory<String, UserEntity>() {

    val dataSource = MutableLiveData<UserKeyedDataSource>()

    override fun create(): DataSource<String, UserEntity> {
        val source = UserKeyedDataSource(localRepository)
        dataSource.postValue(source)
        return source
    }
}