package com.shishkin.itransition.di

import androidx.fragment.app.Fragment
import com.shishkin.itransition.gui.nba.NbaFragment
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi

@Module
class NbaPlayerIdModule {
    @InternalCoroutinesApi
    @Provides
    @NbaPlayerId
    fun provideNbaPlayerId(nbaDetailsFragment: NbaDetailsFragment) : Int? {
      return nbaDetailsFragment.arguments?.getInt("id")
    }
}