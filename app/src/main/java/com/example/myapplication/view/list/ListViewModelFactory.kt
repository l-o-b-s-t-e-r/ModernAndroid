package com.example.myapplication.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.usecases.GetAllUsersUseCase
import com.example.myapplication.domain.usecases.LoadAllUsersUseCase
import com.example.myapplication.domain.usecases.UpdateUserColorUseCase
import javax.inject.Inject

class ListViewModelFactory @Inject constructor(private val getAllUsersUseCase: GetAllUsersUseCase, private val loadAllUsersUseCase: LoadAllUsersUseCase, private val updateUserColorUseCase: UpdateUserColorUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(getAllUsersUseCase, loadAllUsersUseCase, updateUserColorUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}