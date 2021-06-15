package com.shishkin.itransition.di

import com.shishkin.itransition.gui.activities.MainActivity
import com.shishkin.itransition.gui.nba.NbaFragment
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector()
    abstract fun provideNbaFragment(): NbaFragment

    @ContributesAndroidInjector()
    abstract fun provideNbaDetailsFragment(): NbaDetailsFragment

    @ContributesAndroidInjector()
    abstract fun provideMainActivity(): MainActivity

}