package com.shishkin.itransition.di

import androidx.paging.ExperimentalPagingApi
import com.shishkin.itransition.gui.games.NbaGamesFragment
import com.shishkin.itransition.gui.gamesdetails.GamesDetailsFragment
import com.shishkin.itransition.gui.nba.NbaFragment
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.InternalCoroutinesApi

// TODO Evgeny: советую делать не общий FragmentsModule, а для каждого фрагмента делать отдельный модуль.
// соотв. FragmentsModule должен быть удален.

@ExperimentalPagingApi
@InternalCoroutinesApi
@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector()
    abstract fun provideNbaFragment(): NbaFragment

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