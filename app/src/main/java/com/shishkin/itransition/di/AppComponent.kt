package com.shishkin.itransition.di

import androidx.paging.ExperimentalPagingApi
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalPagingApi
@InternalCoroutinesApi
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        RepositoryModule::class,
        NbaPlayerIdModule::class,
        DataBaseModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder

    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

        abstract override fun build(): AppComponent
    }

}
