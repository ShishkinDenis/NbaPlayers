package com.shishkin.itransition.di

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }
//TODO memory leak
    companion object {
        var context: Context? = null
            private set
    }

    override fun applicationInjector(): AndroidInjector<MyApplication> =
            DaggerAppComponent
                    .builder()
                    .create(this)
}

