package com.example.myapplication.domain.usecases

import androidx.paging.PagedList
import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.data.repositories.remote.IRemoteRepository
import com.example.myapplication.domain.UserMapper
import io.reactivex.Completable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UpdateAllUsersUseCase @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val localRepository: ILocalRepository,
    private val userMapper: UserMapper
) {

    fun execute(config: PagedList.Config) =
        Single.just(remoteRepository.getAllUsersWithLimit(config.initialLoadSizeHint))
            .delay(2, TimeUnit.SECONDS)
            /*.map { throw Exception() }*/ //Uncomment this to see error icon
            .map { users ->
                users.map { userMapper.toEntity(it) }
            }
            .flatMapCompletable { users ->
                Completable.fromAction { localRepository.updateAllUsers(users) }
            }
}