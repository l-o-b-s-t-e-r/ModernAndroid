package com.example.myapplication.domain.usecases

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllUsersPerPageUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) {

    fun execute(config: PagedList.Config): Observable<PagedList<UserEntity>> {
        return RxPagedListBuilder(localRepository.getAllUsersPerPage(), config).buildObservable()
    }
}