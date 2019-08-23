package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.entities.User
import javax.inject.Inject

class UpdateUserColorUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(user: User, color: Int) =
        localRepository.updateUserColor(user.id, color)

}