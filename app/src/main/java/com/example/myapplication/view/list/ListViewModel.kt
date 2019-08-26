package com.example.myapplication.view.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.entities.User
import com.example.myapplication.domain.usecases.GetAllUsersUseCase
import com.example.myapplication.domain.usecases.LoadAndSaveAllUsersUseCase
import com.example.myapplication.domain.usecases.UpdateUserColorUseCase
import com.example.myapplication.randomColor
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val loadAndSaveAllUsersUseCase: LoadAndSaveAllUsersUseCase,
    private val updateUserColorUseCase: UpdateUserColorUseCase
) : ViewModel(), ListViewListeners {

    val users: Flowable<List<User>> by lazy { getAllUsers() }

    val isLoading = MutableLiveData<Boolean>().apply { postValue(false) }

    val usersLoaded = MutableLiveData<Boolean>().apply { postValue(false) }

    val isError = MutableLiveData<Boolean>()

    val isUsersListEmpty = MutableLiveData<Boolean>()

    @SuppressLint("CheckResult")
    fun getAllUsers(): Flowable<List<User>> {
        return getAllUsersUseCase.execute()
            /*.map { listOf<User>() }*/ //Uncomment this to see empty list title
            .doOnNext { users -> isUsersListEmpty.postValue(users.isEmpty()) }
            .observeOn(AndroidSchedulers.mainThread())
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
    override fun onItemClick(user: User) {
        updateUserColorUseCase.execute(user, randomColor())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("onItemClick", "User with id=${user.id} has been updated.")
            }, { e ->
                e.printStackTrace()
            })
    }
}
