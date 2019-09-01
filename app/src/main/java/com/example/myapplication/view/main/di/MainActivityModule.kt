package com.example.myapplication.view.main.di

import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.view.main.MainActivity
import com.example.myapplication.view.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModel(activity: MainActivity): MainViewModel {
        return ViewModelProviders.of(activity)[MainViewModel::class.java]
    }
}