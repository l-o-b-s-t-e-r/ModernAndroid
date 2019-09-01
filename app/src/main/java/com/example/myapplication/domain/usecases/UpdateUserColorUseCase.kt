package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.dto.UserDto
import javax.inject.Inject

class UpdateUserColorUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(user: UserDto, color: Int) =
        localRepository.updateUserColor(user.id, color)

}