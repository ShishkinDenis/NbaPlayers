package com.shishkin.itransition.di

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<MyApplication> =
            DaggerAppComponent
                    .builder()
                    .create(this)
}

