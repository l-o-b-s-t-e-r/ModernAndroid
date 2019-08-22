package com.example.myapplication.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.usecases.LoadAllUsersUseCase
import javax.inject.Inject

class ListViewModelFactory @Inject constructor(private val loadAllUsersUseCase: LoadAllUsersUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(loadAllUsersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}