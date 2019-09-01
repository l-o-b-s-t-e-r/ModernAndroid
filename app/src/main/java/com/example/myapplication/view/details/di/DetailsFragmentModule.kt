package com.example.myapplication.view.details.di

import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.view.details.DetailsFragment
import com.example.myapplication.view.details.DetailsViewModel
import com.example.myapplication.view.details.DetailsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DetailsFragmentModule {

   @Provides
   fun provideListViewModel(fragment: DetailsFragment, viewModelFactory: DetailsViewModelFactory): DetailsViewModel {
       return ViewModelProviders.of(fragment, viewModelFactory)[DetailsViewModel::class.java]
   }

}