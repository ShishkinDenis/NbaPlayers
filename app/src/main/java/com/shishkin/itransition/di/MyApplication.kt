package com.shishkin.itransition.di

import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.shishkin.itransition.workers.ImageCompressionWorkerFactory
import com.shishkin.itransition.workers.TestWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


class MyApplication : DaggerApplication(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        if (com.shishkin.itransition.BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    @Inject
    lateinit var imageCompressionWorkerFactory: ImageCompressionWorkerFactory

    @Inject
    lateinit var testWorkerFactory: TestWorkerFactory

    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent
            .builder()
            .create(this)

    override fun getWorkManagerConfiguration(): Configuration {
        val delegatingWorkerFactory = DelegatingWorkerFactory()
        delegatingWorkerFactory.addFactory(imageCompressionWorkerFactory)
        delegatingWorkerFactory.addFactory(testWorkerFactory)

        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(delegatingWorkerFactory)
            .build()
    }
}
