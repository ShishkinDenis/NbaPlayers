package com.shishkin.itransition.di

import com.shishkin.itransition.gui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(
            modules = [
                FragmentsModule::class
            ]
    )
    abstract fun provideMainActivity(): MainActivity
}

