package com.example.myapplication.view.main.di

import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.view.main.MainActivity
import com.example.myapplication.view.main.MainViewModel
import com.example.myapplication.view.main.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModel(activity: MainActivity, viewModelFactory: MainViewModelFactory): MainViewModel {
        return ViewModelProviders.of(activity, viewModelFactory)[MainViewModel::class.java]
    }
}
