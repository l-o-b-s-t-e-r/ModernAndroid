package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.entities.UserEntity
import javax.inject.Inject

class UpdateUserColorUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(user: UserEntity, color: Int) =
        localRepository.updateUserColor(user.id, color)

}