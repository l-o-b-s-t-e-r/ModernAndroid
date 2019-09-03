package com.example.myapplication.view.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.myapplication.domain.Event
import com.example.myapplication.domain.EventType
import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.states.RefreshState
import com.example.myapplication.domain.states.UsersState
import com.example.myapplication.domain.usecases.*
import com.example.myapplication.randomColor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ListViewModel(
    private val updateAllUsersUseCase: UpdateAllUsersUseCase,
    private val updateUserColorUseCase: UpdateUserColorUseCase,
    private val getAllUsersPerPageUseCase: GetAllUsersPerPageUseCase,
    private val getUserSearchQueryUseCase: GetUserSearchQueryUseCase,
    private val getUsersStateUseCase: GetUsersStateUseCase,
    private val getRefreshStateUseCase: GetRefreshStateUseCase
) : ViewModel() {

    private val PAGED_LIST_CONFIG = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(20)
        .setPrefetchDistance(3)
        .setPageSize(10)
        .build()

    private var eventDisposable: Disposable? = null

    private val usersStatePublishSubject = getUsersStateUseCase.execute()

    private val refreshStatePublishSubject = getRefreshStateUseCase.execute()

    val eventListener = PublishSubject.create<Event>()

    val searchQuery = getUserSearchQueryUseCase.execute()

    val usersState = MutableLiveData<UsersState>()

    val refreshState = MutableLiveData<RefreshState>()

    val users: LiveData<PagedList<UserDto>> by lazy {
        getAllUsersPerPage(PAGED_LIST_CONFIG)
    }

    private fun getAllUsersPerPage(config: PagedList.Config): LiveData<PagedList<UserDto>> {
        return Transformations.switchMap(searchQuery) {
            getAllUsersPerPageUseCase.execute(config)
        }
    }

    @SuppressLint("CheckResult")
    fun listenStates() {
        usersStatePublishSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { usersState.value = it }

        refreshStatePublishSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it == RefreshState.LOADING) {
                    updateAllUsers()
                }
            }
    }

    @SuppressLint("CheckResult")
    fun updateAllUsers() {
        updateAllUsersUseCase.execute(PAGED_LIST_CONFIG)
            .doFinally { refreshStatePublishSubject.onNext(RefreshState.NOT_LOADING) }
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

    fun listenUiEvents() {
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
}
