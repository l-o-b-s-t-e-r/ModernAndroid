package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Flowable
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(): Flowable<List<UserEntity>> =
        localRepository.getAllUsers()
            .map { users ->
                users.sortedBy { it.name }
            }
}