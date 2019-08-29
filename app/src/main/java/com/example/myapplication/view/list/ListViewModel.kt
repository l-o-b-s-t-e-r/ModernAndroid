package com.example.myapplication.view.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.myapplication.domain.NetworkState
import com.example.myapplication.domain.NotLoading
import com.example.myapplication.domain.RefreshState
import com.example.myapplication.domain.entities.UserEntity
import com.example.myapplication.domain.usecases.GetAllUsersPerPageUseCase
import com.example.myapplication.domain.usecases.UpdateAllUsersUseCase
import com.example.myapplication.domain.usecases.UpdateUserColorUseCase
import com.example.myapplication.randomColor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListViewModel(
    private val updateAllUsersUseCase: UpdateAllUsersUseCase,
    private val updateUserColorUseCase: UpdateUserColorUseCase,
    private val getAllUsersPerPageUseCase: GetAllUsersPerPageUseCase
) : ViewModel(), ListViewListeners {

    private val PAGED_LIST_CONFIG = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(15)
        .setPrefetchDistance(5)
        .setPageSize(5)
        .build()

    val networkState = MutableLiveData<NetworkState>()

    val refreshState = MutableLiveData<RefreshState>()

    val users: LiveData<PagedList<UserEntity>> by lazy {
        getAllUsersPerPage(PAGED_LIST_CONFIG)
    }

    private fun getAllUsersPerPage(config: PagedList.Config): LiveData<PagedList<UserEntity>> {
        return getAllUsersPerPageUseCase.execute(config, networkState)
    }

    @SuppressLint("CheckResult")
    fun updateAllUsers() {
        updateAllUsersUseCase.execute(PAGED_LIST_CONFIG)
            .doFinally { refreshState.postValue(NotLoading) }
            .subscribe({
                Log.i("loadUsers", "Users were updated successfully.")
            }, { e ->
                Log.e("loadUsers", "Users were not updated.")
                e.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    override fun onItemClick(user: UserEntity) {
        updateUserColorUseCase.execute(user, randomColor())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("onItemClick", "UserEntity with id=${user.id} has been updated.")
            }, { e ->
                e.printStackTrace()
            })
    }
}
