package com.example.myapplication.di

import com.example.myapplication.view.details.DetailsFragment
import com.example.myapplication.view.details.di.DetailsFragmentComponent
import com.example.myapplication.view.list.ListFragment
import com.example.myapplication.view.list.di.ListFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


@Module(subcomponents = [ListFragmentComponent::class, DetailsFragmentComponent::class])
abstract class FragmentsBuilder {
    @Binds
    @IntoMap
    @ClassKey(DetailsFragment::class)
    internal abstract fun bindDetailsFragmentComponentFactory(factory: DetailsFragmentComponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(ListFragment::class)
    internal abstract fun bindListFragmentComponentFactory(factory: ListFragmentComponent.Factory): AndroidInjector.Factory<*>
}