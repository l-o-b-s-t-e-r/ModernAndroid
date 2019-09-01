package com.example.myapplication.di

import com.example.myapplication.view.main.MainActivity
import com.example.myapplication.view.main.di.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivityComponent::class])
abstract class ActivitiesBuilder {
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    internal abstract fun bindAndroidInjectorFactory(factory: MainActivityComponent.Factory): AndroidInjector.Factory<*>
}