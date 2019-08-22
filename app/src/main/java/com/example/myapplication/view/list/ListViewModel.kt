package com.example.myapplication.view.list

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.entities.User
import com.example.myapplication.domain.usecases.LoadAllUsersUseCase

class ListViewModel(private val loadAllUsersUseCase: LoadAllUsersUseCase) : ViewModel() {

    var users: MutableLiveData<List<User>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun loadUsers() {
        loadAllUsersUseCase.execute()
            .subscribe { response -> users.value = response}
    }
}
