package com.shishkin.itransition.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        RepositoryModule::class,
        NbaAppModule::class,
        ActivitiesModule::class,
        ApiServiceModule::class,
        DatabaseModule::class,
        ConfigurationProviderModule::class,
        WorkManagerModule::class,
        ValidatorModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

        abstract override fun build(): AppComponent
    }
}
