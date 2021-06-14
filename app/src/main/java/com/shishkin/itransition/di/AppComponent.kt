package com.shishkin.itransition.di

import com.shishkin.itransition.gui.nba.NbaFragment
import com.shishkin.itransition.gui.nba.NbaViewModel
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import com.shishkin.itransition.gui.nbadetails.NbaDetailsViewModel
import dagger.Component
import kotlinx.coroutines.InternalCoroutinesApi

@Component(modules = [RepositoryModule::class])
interface AppComponent {
    @InternalCoroutinesApi
    fun inject(nbaFragment: NbaFragment)
    fun inject(nbaViewModel: NbaViewModel)
    fun inject(nbaDetailsFragment: NbaDetailsFragment)
    fun inject(nbaDetailsViewModel: NbaDetailsViewModel)

}