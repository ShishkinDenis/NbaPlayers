package com.shishkin.itransition.di

import com.shishkin.itransition.R
import com.shishkin.itransition.di.MyApplication.Companion.context
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import dagger.Module
import dagger.Provides

// TODO Evgeny специальные даггер классы, которые относятся к одной специальной фиче, лучше располагать
// в фича-пакете: Тоже самое. Расположить в фича-пакете

@Module
class NbaPlayerIdModule {

    @Provides
    @NbaPlayerId
    fun provideNbaPlayerId(nbaDetailsFragment: NbaDetailsFragment): Int? {
        return nbaDetailsFragment.arguments?.getInt(context?.getString(R.string.nba_player_id))
    }
}