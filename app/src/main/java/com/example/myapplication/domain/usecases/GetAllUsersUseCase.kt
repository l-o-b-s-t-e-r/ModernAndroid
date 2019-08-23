package com.example.myapplication.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.entities.User
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(): LiveData<List<User>> =
        Transformations.map(localRepository.getAllUsers()) { users ->
            users.sortedBy { it.name }
        }
}