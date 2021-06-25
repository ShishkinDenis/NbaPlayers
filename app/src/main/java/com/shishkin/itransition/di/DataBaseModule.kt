package com.shishkin.itransition.di

import android.content.Context
import com.shishkin.itransition.db.NbaPlayerDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
//    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)
    fun provideDatabase(@ApplicationContext appContext: Context) = NbaPlayerDataBase.getDatabase(appContext)
}