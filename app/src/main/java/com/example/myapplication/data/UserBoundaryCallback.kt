package com.example.myapplication.data

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagedList
import com.example.myapplication.data.repositories.remote.IRemoteRepository
import com.example.myapplication.domain.UserMapper
import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.entities.UserEntity
import com.example.myapplication.domain.states.NetworkState
import com.example.myapplication.domain.states.UsersState
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class UserBoundaryCallback(
    private val saveResponse: (List<UserEntity>) -> Unit,
    private val remoteRepository: IRemoteRepository,
    private val config: PagedList.Config,
    private val userMapper: UserMapper,
    private val query: String,
    private val usersState: PublishSubject<UsersState>
) : PagedList.BoundaryCallback<UserDto>() {

    @SuppressLint("CheckResult")
    override fun onZeroItemsLoaded() {
        Log.e("UserBoundaryCallback", "onZeroItemsLoaded")
        usersState.onNext(NetworkState.LOADING)
        Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe({
                val response = remoteRepository.getAllUsersWithLimit(query, config.initialLoadSizeHint)
                saveResponse(response.map { userMapper.toEntity(it) })
                usersState.onNext(NetworkState.LOADED)
            },{
                it.printStackTrace()
                usersState.onNext(NetworkState.error(it.message))
            })
    }

    @SuppressLint("CheckResult")
    override fun onItemAtEndLoaded(itemAtEnd: UserDto) {
        Log.e("UserBoundaryCallback", "onItemAtEndLoaded ${itemAtEnd.name}")
        usersState.onNext(NetworkState.LOADING)
        Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe({
                val response = remoteRepository.getAllUsersAfterWithLimit(query, itemAtEnd.name, config.pageSize)
                saveResponse(response.map { userMapper.toEntity(it) })
                usersState.onNext(NetworkState.LOADED)
            },{
                it.printStackTrace()
                usersState.onNext(NetworkState.error(it.message))
            })
    }
}