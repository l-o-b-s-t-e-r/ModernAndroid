package com.example.myapplication.view.list.di

import com.example.myapplication.di.BaseViewModule
import com.example.myapplication.view.list.ListFragment
import com.example.myapplication.view.list.ListViewModel
import com.example.myapplication.view.list.ListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ListFragmentModule : BaseViewModule<ListFragment, ListViewModel, ListViewModelFactory>() {

    @Provides
    fun provideListViewModel(fragment: ListFragment, viewModelFactory: ListViewModelFactory): ListViewModel = getViewModel(fragment, viewModelFactory)

}