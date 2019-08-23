package com.example.myapplication.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.AppDatabase.Migrations.MIGRATION_1_2
import com.example.myapplication.data.repositories.local.ILocalRepository
import com.example.myapplication.data.repositories.local.LocalRepository
import com.example.myapplication.data.repositories.remote.IRemoteRepository
import com.example.myapplication.data.repositories.remote.RemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(): IRemoteRepository =
        RemoteRepository()

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java, "database-name"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(db: AppDatabase): ILocalRepository =
        LocalRepository(db)
}