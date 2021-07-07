package com.shishkin.itransition.gui.gamesdetails

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NbaGamesDetailsFragmentModule {

    @ContributesAndroidInjector()
    abstract fun provideNbaGamesDetailsFragment(): GamesDetailsFragment
}