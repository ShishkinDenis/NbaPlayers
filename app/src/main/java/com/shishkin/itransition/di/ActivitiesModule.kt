package com.shishkin.itransition.di

import androidx.paging.ExperimentalPagingApi
import com.shishkin.itransition.gui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalPagingApi
@InternalCoroutinesApi
@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(
            modules = [
                FragmentsModule::class
            ]
    )
    abstract fun provideMainActivity(): MainActivity

}

