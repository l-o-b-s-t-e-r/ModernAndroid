package com.example.myapplication.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.data.UserDataSourceFactory
import com.example.myapplication.domain.entities.UserEntity
import javax.inject.Inject

class GetAllUsersPerPageByNameUseCase @Inject constructor() {

    fun execute(dataSource: UserDataSourceFactory,config: PagedList.Config): LiveData<PagedList<UserEntity>> {
        return LivePagedListBuilder(dataSource, config).build()
    }

}