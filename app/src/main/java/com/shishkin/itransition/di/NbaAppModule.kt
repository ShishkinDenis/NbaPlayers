package com.shishkin.itransition.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Application

@Module
class NbaAppModule {

    @Provides
    @Application
    fun provideApplicationContext(app: MyApplication): Context {
        return app.applicationContext
    }
}