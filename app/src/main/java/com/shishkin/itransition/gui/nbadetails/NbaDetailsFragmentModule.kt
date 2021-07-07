package com.shishkin.itransition.gui.nbadetails

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NbaDetailsFragmentModule {

    @ContributesAndroidInjector(
        modules = [
            NbaPlayerIdModule::class
        ]
    )
    abstract fun provideNbaDetailsFragment(): NbaDetailsFragment
}