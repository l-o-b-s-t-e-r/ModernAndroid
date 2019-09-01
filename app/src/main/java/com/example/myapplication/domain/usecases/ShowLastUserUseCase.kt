package com.example.myapplication.domain.usecases

import com.example.myapplication.data.repositories.local.ILocalRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShowLastUserUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute() =
        localRepository.showLastUser()
            .subscribeOn(Schedulers.io())

}