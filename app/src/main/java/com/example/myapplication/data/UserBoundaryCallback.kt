package com.example.myapplication.data

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.myapplication.data.repositories.remote.IRemoteRepository
import com.example.myapplication.domain.NetworkState
import com.example.myapplication.domain.UserMapper
import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class UserBoundaryCallback(
    private val saveResponse: (List<UserEntity>) -> Unit,
    private val remoteRepository: IRemoteRepository,
    private val config: PagedList.Config,
    private val networkState: MutableLiveData<NetworkState>,
    private val userMapper: UserMapper
) : PagedList.BoundaryCallback<UserDto>() {

    @SuppressLint("CheckResult")
    override fun onZeroItemsLoaded() {
        Log.e("UserBoundaryCallback", "onZeroItemsLoaded")
        networkState.postValue(NetworkState.LOADING)
        Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe({
                Log.e("UserBoundaryCallback", "onZeroItemsLoaded")
                val response = remoteRepository.getAllUsersWithLimit(config.initialLoadSizeHint)
                saveResponse(response.map { userMapper.toEntity(it) })
                networkState.postValue(NetworkState.LOADED)
            },{
                it.printStackTrace()
                networkState.postValue(NetworkState.error(it.message))
            })
    }

    @SuppressLint("CheckResult")
    override fun onItemAtEndLoaded(itemAtEnd: UserDto) {
        Log.e("UserBoundaryCallback", "onItemAtEndLoaded ${itemAtEnd.name}")
        networkState.postValue(NetworkState.LOADING)
        Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe({
                Log.e("UserBoundaryCallback", "onItemAtEndLoaded")
                val response = remoteRepository.getAllUsersAfterWithLimit(itemAtEnd.name, config.pageSize)
                saveResponse(response.map { userMapper.toEntity(it) })
                networkState.postValue(NetworkState.LOADED)
            },{
                it.printStackTrace()
                networkState.postValue(NetworkState.error(it.message))
            })
    }
}