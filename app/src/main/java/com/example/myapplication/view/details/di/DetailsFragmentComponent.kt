package com.example.myapplication.view.details.di

import com.example.myapplication.view.details.DetailsFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [DetailsFragmentModule::class])
interface DetailsFragmentComponent : AndroidInjector<DetailsFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<DetailsFragment>

}