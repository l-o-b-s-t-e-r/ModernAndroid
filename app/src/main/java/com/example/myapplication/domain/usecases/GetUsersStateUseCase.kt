package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import javax.inject.Inject

class GetUsersStateUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute() = localRepository.getUsersState()

}