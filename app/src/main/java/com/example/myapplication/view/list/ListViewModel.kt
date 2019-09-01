package com.example.myapplication.view.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.myapplication.domain.*
import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.usecases.GetAllUsersPerPageUseCase
import com.example.myapplication.domain.usecases.UpdateAllUsersUseCase
import com.example.myapplication.domain.usecases.UpdateUserColorUseCase
import com.example.myapplication.randomColor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ListViewModel(
    private val updateAllUsersUseCase: UpdateAllUsersUseCase,
    private val updateUserColorUseCase: UpdateUserColorUseCase,
    private val getAllUsersPerPageUseCase: GetAllUsersPerPageUseCase
) : ViewModel() {

    private val PAGED_LIST_CONFIG = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(15)
        .setPrefetchDistance(5)
        .setPageSize(5)
        .build()

    val eventListener = PublishSubject.create<Event>()
    private var eventDisposable: Disposable? = null

    val networkState = MutableLiveData<NetworkState>()

    val refreshState = MutableLiveData<RefreshState>()

    val users: LiveData<PagedList<UserDto>> by lazy {
        getAllUsersPerPage(PAGED_LIST_CONFIG)
    }

    fun startListenEvents() {
        eventDisposable = eventListener.subscribe { event ->
            when (event.type) {
                EventType.ITEM_CLICK -> {
                    if (event.obj is UserDto) {
                        onItemClick(event.obj)
                    }
                }
                EventType.ICON_CLICK -> {
                    if (event.obj is UserDto) {
                        onIconClick(event.obj)
                    }
                }
            }
        }
    }

    private fun getAllUsersPerPage(config: PagedList.Config): LiveData<PagedList<UserDto>> {
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
    private fun onItemClick(user: UserDto) {
        updateUserColorUseCase.execute(user, randomColor())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("onItemClick", "UserEntity with id=${user.id} has been updated.")
            }, { e ->
                e.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun onIconClick(user: UserDto) {
        updateUserColorUseCase.execute(user, -1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("onIconClick", "UserEntity with id=${user.id} has been updated.")
            }, { e ->
                e.printStackTrace()
            })
    }

    override fun onCleared() {
        eventDisposable?.apply {
            if (isDisposed.not()) {
                dispose()
            }
        }
    }
}
