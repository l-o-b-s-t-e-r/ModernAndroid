package com.example.myapplication.di

import com.example.myapplication.view.list.ListFragment
import com.example.myapplication.view.list.di.ListFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


@Module(subcomponents = [ListFragmentComponent::class])
abstract class FragmentsBuilder {
    @Binds
    @IntoMap
    @ClassKey(ListFragment::class)
    internal abstract fun bindAndroidInjectorFactory(factory: ListFragmentComponent.Factory): AndroidInjector.Factory<*>
}