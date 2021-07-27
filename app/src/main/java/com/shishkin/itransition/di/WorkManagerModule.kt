package com.shishkin.itransition.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides

@Module
class WorkManagerModule {

    @Provides
    fun provideWorkManager(@Application context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}