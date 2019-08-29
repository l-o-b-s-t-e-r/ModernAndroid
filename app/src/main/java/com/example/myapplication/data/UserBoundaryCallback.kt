package com.example.myapplication.data

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.myapplication.data.repositories.remote.IRemoteRepository
import com.example.myapplication.domain.NetworkState
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class UserBoundaryCallback(
    private val saveResponse: (List<UserEntity>) -> Unit,
    private val remoteRepository: IRemoteRepository,
    private val config: PagedList.Config,
    private val networkState: MutableLiveData<NetworkState>
) : PagedList.BoundaryCallback<UserEntity>() {

    @SuppressLint("CheckResult")
    override fun onZeroItemsLoaded() {
        Log.e("UserBoundaryCallback", "onZeroItemsLoaded")
        networkState.postValue(NetworkState.LOADING)
        Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe({
                Log.e("UserBoundaryCallback", "onZeroItemsLoaded")
                saveResponse(remoteRepository.getAllUsersWithLimit(config.initialLoadSizeHint))
                networkState.postValue(NetworkState.LOADED)
            },{
                it.printStackTrace()
                networkState.postValue(NetworkState.error(it.message))
            })

        /*Observable.just(remoteRepository.getAllUsersWithLimit(pageSize))
            .delay(2, TimeUnit.SECONDS)
            .subscribe { localRepository.saveAllUsers(it) }*/
    }

    @SuppressLint("CheckResult")
    override fun onItemAtEndLoaded(itemAtEnd: UserEntity) {
        Log.e("UserBoundaryCallback", "onItemAtEndLoaded ${itemAtEnd.name}")
        networkState.postValue(NetworkState.LOADING)
        Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe({
                Log.e("UserBoundaryCallback", "onItemAtEndLoaded")
                saveResponse(remoteRepository.getAllUsersAfterWithLimit(itemAtEnd.name, config.pageSize))
                networkState.postValue(NetworkState.LOADED)
            },{
                it.printStackTrace()
                networkState.postValue(NetworkState.error(it.message))
            })

        /*Observable.just(remoteRepository.getAllUsersAfterWithLimit(itemAtEnd.name, pageSize))
            .delay(2, TimeUnit.SECONDS)
            .subscribe { localRepository.saveAllUsers(it) }*/
    }
}