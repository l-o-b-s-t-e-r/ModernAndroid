package com.example.myapplication.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.dto.UserDto
import javax.inject.Inject

class GetAllUsersPerPageUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(config: PagedList.Config): LiveData<PagedList<UserDto>> {
        return localRepository.getAllUsersPerPage(config)
    }
}