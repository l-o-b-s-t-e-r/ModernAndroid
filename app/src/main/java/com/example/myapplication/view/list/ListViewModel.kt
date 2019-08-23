package com.example.myapplication.view.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.entities.User
import com.example.myapplication.domain.usecases.GetAllUsersUseCase
import com.example.myapplication.domain.usecases.LoadAllUsersUseCase
import com.example.myapplication.domain.usecases.UpdateUserColorUseCase
import com.example.myapplication.randomColor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val loadAllUsersUseCase: LoadAllUsersUseCase,
    private val updateUserColorUseCase: UpdateUserColorUseCase
) : ViewModel(), ListViewListeners {

    var users: LiveData<List<User>> = getAllUsersUseCase.execute()

    @SuppressLint("CheckResult")
    fun loadUsers() {
        loadAllUsersUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("loadUsers", "Users was loaded successfully.")
            }, { e ->
                e.printStackTrace()
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
