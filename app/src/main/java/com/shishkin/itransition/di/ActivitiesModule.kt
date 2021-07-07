package com.shishkin.itransition.di

import com.shishkin.itransition.gui.activities.MainActivity
import com.shishkin.itransition.gui.games.NbaGamesFragmentModule
import com.shishkin.itransition.gui.gamesdetails.NbaGamesDetailsFragmentModule
import com.shishkin.itransition.gui.nba.NbaFragmentModule
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(
        modules = [
            NbaFragmentModule::class,
            NbaDetailsFragmentModule::class,
            NbaGamesFragmentModule::class,
            NbaGamesDetailsFragmentModule::class
        ]
    )
    abstract fun provideMainActivity(): MainActivity
}

