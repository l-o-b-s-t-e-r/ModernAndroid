package com.example.myapplication.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.usecases.GetAllUsersPerPageUseCase
import com.example.myapplication.domain.usecases.UpdateAllUsersUseCase
import com.example.myapplication.domain.usecases.UpdateUserColorUseCase
import javax.inject.Inject

class ListViewModelFactory @Inject constructor(private val updateAllUsersUseCase: UpdateAllUsersUseCase,
                                               private val updateUserColorUseCase: UpdateUserColorUseCase,
                                               private val getAllUsersPerPageUseCase: GetAllUsersPerPageUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(updateAllUsersUseCase, updateUserColorUseCase, getAllUsersPerPageUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}