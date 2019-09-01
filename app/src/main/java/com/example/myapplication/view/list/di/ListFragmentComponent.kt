package com.example.myapplication.view.list.di

import com.example.myapplication.view.list.ListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [ListFragmentModule::class])
interface ListFragmentComponent : AndroidInjector<ListFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<ListFragment>

}