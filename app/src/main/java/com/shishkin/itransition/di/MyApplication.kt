package com.shishkin.itransition.di


import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.InternalCoroutinesApi

class MyApplication : DaggerApplication() {

    @InternalCoroutinesApi
    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent
            .builder()
            .create(this)

}
