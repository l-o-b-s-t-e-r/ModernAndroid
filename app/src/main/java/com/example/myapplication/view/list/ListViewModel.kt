package com.example.myapplication.view.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.myapplication.domain.Event
import com.example.myapplication.domain.EventPublisher
import com.example.myapplication.domain.IconClickFilter
import com.example.myapplication.domain.ItemClickFilter
import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.states.RefreshState
import com.example.myapplication.domain.states.UsersState
import com.example.myapplication.domain.usecases.*
import com.example.myapplication.randomColor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListViewModel(
    private val eventPublisher: EventPublisher,
    private val itemClickFilter: ItemClickFilter,
    private val iconClickFilter: IconClickFilter,
    private val updateAllUsersUseCase: UpdateAllUsersUseCase,
    private val updateUserColorUseCase: UpdateUserColorUseCase,
    private val getAllUsersPerPageUseCase: GetAllUsersPerPageUseCase,
    private val getUserSearchQueryUseCase: GetUserSearchQueryUseCase,
    private val getUsersStateUseCase: GetUsersStateUseCase,
    private val getRefreshStateUseCase: GetRefreshStateUseCase
) : ViewModel() {

    private val PAGED_LIST_CONFIG = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(10)
        .setPrefetchDistance(1)
        .setPageSize(10)
        .build()

    private var eventDisposable: Disposable? = null

    private val usersState = getUsersStateUseCase.execute()

    private val refreshState = getRefreshStateUseCase.execute()

    val searchQuery = getUserSearchQueryUseCase.execute()

    val usersStateLiveData = MutableLiveData<UsersState>()

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
        usersState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { usersStateLiveData.value = it }

        refreshState
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
            .doFinally { refreshState.onNext(RefreshState.NOT_LOADING) }
            .subscribe({
                Log.i("loadUsers", "Users were updated successfully.")
            }, { e ->
                Log.e("loadUsers", "Users were not updated.")
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

    @SuppressLint("CheckResult")
    fun listenUiEvents() {
        itemClickFilter.filter()
            .subscribe { onItemClick(it) }

        iconClickFilter.filter()
            .subscribe { onIconClick(it) }
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


    fun sendEvent(event: Event) {
        eventPublisher.send(event)
    }
}
