package com.example.myapplication.di

import com.example.myapplication.view.list.ListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    fun inject(fragment: ListFragment)

}