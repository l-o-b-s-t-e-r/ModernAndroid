package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.dto.UserDto
import javax.inject.Inject

class DeleteUserByIdUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(user: UserDto) =
        localRepository.deleteUserById(user.id)

}