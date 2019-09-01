package com.example.myapplication.view.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.usecases.DeleteFirstUserUseCase
import com.example.myapplication.domain.usecases.HideFirstUserUseCase
import com.example.myapplication.domain.usecases.ShowLastUserUseCase
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(private val deleteFirstUserUseCase: DeleteFirstUserUseCase,
                                                  private val showLastUserUseCase: ShowLastUserUseCase,
                                                  private val hideFirstUserUseCase: HideFirstUserUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(deleteFirstUserUseCase, showLastUserUseCase, hideFirstUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}