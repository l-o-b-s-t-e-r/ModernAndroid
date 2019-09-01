package com.example.myapplication.view.main.di

import com.example.myapplication.di.FragmentsBuilder
import com.example.myapplication.view.main.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [MainActivityModule::class, FragmentsBuilder::class])
interface MainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>

}