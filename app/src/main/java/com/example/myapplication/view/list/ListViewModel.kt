package com.example.myapplication.view.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.myapplication.data.UserDataSourceFactory
import com.example.myapplication.domain.entities.UserEntity
import com.example.myapplication.domain.usecases.*
import com.example.myapplication.randomColor
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val loadAndSaveAllUsersUseCase: LoadAndSaveAllUsersUseCase,
    private val updateUserColorUseCase: UpdateUserColorUseCase,
    private val getAllUsersPerPageUseCase: GetAllUsersPerPageUseCase,
    private val getAllUsersPerPageByNameUseCase: GetAllUsersPerPageByNameUseCase,
    private val dataSource: UserDataSourceFactory
) : ViewModel(), ListViewListeners {

    private val PAGED_LIST_CONFIG = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(15)
        .setPrefetchDistance(5)
        .setPageSize(5)
        .build()

    val users: Flowable<List<UserEntity>> by lazy { getAllUsers() }

    val usersPaged: Observable<PagedList<UserEntity>> by lazy { getAllUsersPerPage(PAGED_LIST_CONFIG) }

    val usersPagedByName: LiveData<PagedList<UserEntity>> by lazy { getAllUsersPerPageByName(PAGED_LIST_CONFIG) }

    val isLoading = MutableLiveData<Boolean>().apply { postValue(false) }

    val usersLoaded = MutableLiveData<Boolean>().apply { postValue(false) }

    val isError = MutableLiveData<Boolean>()

    val isUsersListEmpty = MutableLiveData<Boolean>()

    @SuppressLint("CheckResult")
    private fun getAllUsers(): Flowable<List<UserEntity>> {
        return getAllUsersUseCase.execute()
            /*.map { listOf<UserEntity>() }*/ //Uncomment this to see empty list title
            .doOnNext { users -> isUsersListEmpty.postValue(users.isEmpty()) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getAllUsersPerPage(config: PagedList.Config): Observable<PagedList<UserEntity>> {
        return getAllUsersPerPageUseCase.execute(config)
            .doOnNext { users -> isUsersListEmpty.postValue(users.isEmpty()) }
    }

    private fun getAllUsersPerPageByName(config: PagedList.Config): LiveData<PagedList<UserEntity>> {
        return getAllUsersPerPageByNameUseCase.execute(dataSource, config)
    }

    @SuppressLint("CheckResult")
    fun loadAllUsers() {
        loadAndSaveAllUsersUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (isUsersListEmpty.value == null && isError.value == null) {
                    isLoading.postValue(true)
                }
            }
            .doFinally {
                isLoading.postValue(false)
            }
            .subscribe({
                Log.i("loadUsers", "Users were loaded successfully.")
                usersLoaded.postValue(true)
            }, { e ->
                Log.e("loadUsers", "Users were not loaded.")
                e.printStackTrace()

                isError.postValue(true)
            })
    }

    @SuppressLint("CheckResult")
    override fun onItemClick(user: UserEntity) {
        updateUserColorUseCase.execute(user, randomColor())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("onItemClick", "UserEntity with id=${user.id} has been updated.")
                dataSource.dataSource.value!!.invalidate()
            }, { e ->
                e.printStackTrace()
            })
    }
}
