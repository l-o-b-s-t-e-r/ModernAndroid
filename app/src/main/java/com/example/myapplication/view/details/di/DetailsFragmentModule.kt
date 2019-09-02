package com.example.myapplication.view.details.di

import com.example.myapplication.di.BaseViewModule
import com.example.myapplication.view.details.DetailsFragment
import com.example.myapplication.view.details.DetailsViewModel
import com.example.myapplication.view.details.DetailsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DetailsFragmentModule : BaseViewModule<DetailsFragment, DetailsViewModel, DetailsViewModelFactory>() {

    @Provides
    fun provideViewModel(fragment: DetailsFragment, viewModelFactory: DetailsViewModelFactory): DetailsViewModel = getViewModel(fragment, viewModelFactory)

}