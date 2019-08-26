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
import java.util.concurrent.TimeUnit

class ListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val loadAndSaveAllUsersUseCase: LoadAndSaveAllUsersUseCase,
    private val updateUserColorUseCase: UpdateUserColorUseCase
) : ViewModel(), ListViewListeners {

    val users: Flowable<List<User>> by lazy { getAllUsers() }

    var isLoading = MutableLiveData<Boolean>().apply { postValue(false) }

    var usersLoaded = MutableLiveData<Boolean>().apply { postValue(false) }

    var isError = MutableLiveData<Boolean>().apply { postValue(false) }

    @SuppressLint("CheckResult")
    fun getAllUsers(): Flowable<List<User>> {
        return getAllUsersUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
    }

    @SuppressLint("CheckResult")
    fun loadAllUsers() {
        loadAndSaveAllUsersUseCase.execute()
            .delay(2, TimeUnit.SECONDS)
            .retry(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.postValue(true) }
            .doFinally { isLoading.postValue(false) }
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
