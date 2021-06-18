package com.shishkin.itransition.di

import com.shishkin.itransition.R
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi

@Module
class NbaPlayerIdModule {
    @InternalCoroutinesApi
    @Provides
    @NbaPlayerId
    fun provideNbaPlayerId(nbaDetailsFragment: NbaDetailsFragment): Int? {
        return nbaDetailsFragment.arguments?.getInt("id")
    }
}