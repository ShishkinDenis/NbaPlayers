package com.shishkin.itransition.di

import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.shishkin.itransition.BuildConfig
import com.shishkin.itransition.workers.ImageCompressionWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class MyApplication : DaggerApplication(), Configuration.Provider {

    @Inject
    lateinit var imageCompressionWorkerFactory: ImageCompressionWorkerFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent
            .builder()
            .create(this)

    override fun getWorkManagerConfiguration(): Configuration {
        val delegatingWorkerFactory = DelegatingWorkerFactory()
        delegatingWorkerFactory.addFactory(imageCompressionWorkerFactory)

        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(delegatingWorkerFactory)
            .build()
    }
}
