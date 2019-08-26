package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.data.repositories.remote.IRemoteRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoadAndSaveAllUsersUseCase @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val localRepository: ILocalRepository
) {

    fun execute() =
        remoteRepository.loadAllUsers()
            .delay(2, TimeUnit.SECONDS)
            /*.map { throw Exception() }*/ //Uncomment this to see error icon
            .flatMapCompletable { users ->
                localRepository.saveAllUsers(users)
            }
}