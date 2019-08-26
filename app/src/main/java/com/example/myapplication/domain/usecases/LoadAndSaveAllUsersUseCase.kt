package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.data.repositories.remote.IRemoteRepository
import javax.inject.Inject

class LoadAndSaveAllUsersUseCase @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val localRepository: ILocalRepository
) {

    fun execute() =
        remoteRepository.loadAllUsers()
            .flatMapCompletable { users ->
                localRepository.saveAllUsers(users)
            }
}