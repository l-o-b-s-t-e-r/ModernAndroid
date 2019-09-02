package com.example.myapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.view.base.BaseFragment

abstract class BaseViewModule<F : BaseFragment<*, *>, VM : ViewModel, VMF : ViewModelProvider.Factory> {

    inline fun <reified VM : ViewModel> getViewModel(fragment: F, viewModelFactory: VMF): VM {
        return ViewModelProviders.of(fragment, viewModelFactory)[VM::class.java]
    }
}