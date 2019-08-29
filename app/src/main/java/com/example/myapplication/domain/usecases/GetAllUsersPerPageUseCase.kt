package com.example.myapplication.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.NetworkState
import com.example.myapplication.domain.entities.UserEntity
import javax.inject.Inject

class GetAllUsersPerPageUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(config: PagedList.Config, networkState: MutableLiveData<NetworkState>): LiveData<PagedList<UserEntity>> {
        return localRepository.getAllUsersPerPage(config, networkState)
    }
}