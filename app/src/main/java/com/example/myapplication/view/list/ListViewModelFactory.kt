package com.example.myapplication.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.usecases.GetAllUsersUseCase
import com.example.myapplication.domain.usecases.LoadAndSaveAllUsersUseCase
import com.example.myapplication.domain.usecases.UpdateUserColorUseCase
import javax.inject.Inject

class ListViewModelFactory @Inject constructor(private val getAllUsersUseCase: GetAllUsersUseCase, private val loadAndSaveAllUsersUseCase: LoadAndSaveAllUsersUseCase, private val updateUserColorUseCase: UpdateUserColorUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(getAllUsersUseCase, loadAndSaveAllUsersUseCase, updateUserColorUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}