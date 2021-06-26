package com.shishkin.itransition.di


import androidx.paging.ExperimentalPagingApi
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalPagingApi
class MyApplication : DaggerApplication() {


    @InternalCoroutinesApi
    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent
//            .application(this)
            .builder()
            .create(this)

}


