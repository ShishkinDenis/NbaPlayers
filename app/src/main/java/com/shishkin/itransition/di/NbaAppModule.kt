package com.shishkin.itransition.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Application

@Module
class NbaAppModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Application
    fun provideApplicationContext(app: MyApplication) : Context {
        return app.applicationContext
    }
}