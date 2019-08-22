package com.example.myapplication.domain.usecases

import com.example.myapplication.data.IRemoteRepository
import javax.inject.Inject

class LoadAllUsersUseCase @Inject constructor(private val remoteRepository: IRemoteRepository) {

    fun execute() =
        remoteRepository.loadAllUsers()
            .map { user ->
                user.sortedBy { it.name }
            }

}