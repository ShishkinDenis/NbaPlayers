package com.shishkin.itransition.di

import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.shishkin.itransition.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


class MyApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        try {
            configureWorkManager()
        }
        catch (throwable: Throwable){
            throwable.message
        }
    }

    @Inject
    lateinit var workerFactory: WorkerFactory

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)
    }

    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent
            .builder()
            .create(this)

}

