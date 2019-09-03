package com.example.myapplication.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.usecases.GetRefreshStateUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val getRefreshStateUseCase: GetRefreshStateUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(getRefreshStateUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}