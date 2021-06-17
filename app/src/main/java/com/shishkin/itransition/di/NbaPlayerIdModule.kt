package com.shishkin.itransition.di

import androidx.fragment.app.Fragment
import com.shishkin.itransition.gui.nba.NbaFragment
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi

@Module
class NbaPlayerIdModule {
    val nbaDetailsFragment : NbaDetailsFragment = NbaDetailsFragment()

    @InternalCoroutinesApi
    @Provides
    @NbaPlayerId
//    fun provideNbaPlayerId(nbaDetailsFragment: NbaDetailsFragment) : Int? {
    fun provideNbaPlayerId() : Int? {
      return nbaDetailsFragment.arguments?.getInt("id")
    }
}