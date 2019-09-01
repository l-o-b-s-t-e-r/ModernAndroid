package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HideFirstUserUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute() =
        localRepository.hideFirstUser()
            .subscribeOn(Schedulers.io())

}