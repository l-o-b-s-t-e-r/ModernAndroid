package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeleteFirstUserUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute() =
        localRepository.deleteFirstUser()
            .subscribeOn(Schedulers.io())

}