package com.shishkin.itransition.di

import com.shishkin.itransition.gui.games.NbaGamesFragment
import com.shishkin.itransition.gui.gamesdetails.GamesDetailsFragment
import com.shishkin.itransition.gui.nba.NbaFragmentPagination
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector()
    abstract fun provideNbaFragment(): NbaFragmentPagination

    @ContributesAndroidInjector(
        modules = [
            NbaPlayerIdModule::class
        ]
    )
    abstract fun provideNbaDetailsFragment(): NbaDetailsFragment

    @ContributesAndroidInjector()
    abstract fun provideNbaGamesFragment(): NbaGamesFragment

    @ContributesAndroidInjector()
    abstract fun provideNbaGamesDetailsFragment(): GamesDetailsFragment
}