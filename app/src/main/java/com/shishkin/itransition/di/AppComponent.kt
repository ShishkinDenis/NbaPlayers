package com.shishkin.itransition.di

import com.shishkin.itransition.gui.nba.NbaFragment
import com.shishkin.itransition.gui.nba.NbaViewModel
import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import com.shishkin.itransition.gui.nbadetails.NbaDetailsViewModel
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Component(modules = [
    AndroidSupportInjectionModule::class,
    FragmentsModule::class,
    RepositoryModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder

    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

        abstract override fun build(): AppComponent
    }

}
