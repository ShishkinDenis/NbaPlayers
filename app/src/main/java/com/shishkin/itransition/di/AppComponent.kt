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
        NbaPlayerIdModule::class

    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

//    TODO for providing context for DB
//    override fun inject(application: MyApplication)


    @Component.Builder

    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

//        @BindsInstance
//        abstract fun application(myApplication: MyApplication)

        abstract override fun build(): AppComponent
    }

}
