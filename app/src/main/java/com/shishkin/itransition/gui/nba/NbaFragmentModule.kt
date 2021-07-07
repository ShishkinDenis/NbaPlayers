package com.shishkin.itransition.gui.nba

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NbaFragmentModule {

    @ContributesAndroidInjector()
    abstract fun provideNbaFragment(): NbaFragment
}