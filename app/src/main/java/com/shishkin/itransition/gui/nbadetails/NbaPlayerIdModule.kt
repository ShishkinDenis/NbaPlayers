package com.shishkin.itransition.gui.nbadetails

import com.shishkin.itransition.R
import dagger.Module
import dagger.Provides

@Module
class NbaPlayerIdModule {

    @Provides
    @NbaPlayerId
    fun provideNbaPlayerId(nbaDetailsFragment: NbaDetailsFragment): Int? {
        return nbaDetailsFragment.arguments?.getInt(nbaDetailsFragment.getString(R.string.arg_nba_player_id))
    }
}