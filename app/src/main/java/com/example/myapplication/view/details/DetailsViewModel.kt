package com.example.myapplication.view.details

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.usecases.HideFirstUserUseCase
import com.example.myapplication.domain.usecases.ShowLastUserUseCase

class DetailsViewModel(private val showLastUserUseCase: ShowLastUserUseCase,
                       private val hideFirstUserUseCase: HideFirstUserUseCase) : ViewModel() {

    @SuppressLint("CheckResult")
    fun onDeleteClick() {
        hideFirstUserUseCase.execute()
            .subscribe({

            }, { e ->
                e.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun onRestoreClick() {
        showLastUserUseCase.execute()
            .subscribe({

            }, { e ->
                e.printStackTrace()
            })
    }
}
