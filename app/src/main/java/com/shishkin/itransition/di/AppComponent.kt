package com.shishkin.itransition.di

import com.shishkin.itransition.gui.edituserprofile.EditUserProfileFragmentModule
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
//        TODO почему нужно явно сюда добавить EditUserProfileFragmentModule? он же уже есть в  ActivitiesModule
        EditUserProfileFragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

        abstract override fun build(): AppComponent
    }
}
