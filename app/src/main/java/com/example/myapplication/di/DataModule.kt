package com.example.myapplication.di

import com.example.myapplication.data.IRemoteRepository
import com.example.myapplication.data.RemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(): IRemoteRepository = RemoteRepository()

}