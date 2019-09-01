package com.example.myapplication.view.list.di

import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.view.list.ListFragment
import com.example.myapplication.view.list.ListViewModel
import com.example.myapplication.view.list.ListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ListFragmentModule {

   @Provides
   fun provideListViewModel(fragment: ListFragment, viewModelFactory: ListViewModelFactory): ListViewModel {
       return ViewModelProviders.of(fragment, viewModelFactory)[ListViewModel::class.java]
   }

}