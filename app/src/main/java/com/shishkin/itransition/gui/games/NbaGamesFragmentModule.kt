package com.shishkin.itransition.gui.games

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NbaGamesFragmentModule {

    @ContributesAndroidInjector
    abstract fun provideNbaGamesFragment(): NbaGamesFragment
}