package com.shishkin.itransition.di

import androidx.work.WorkerFactory
import com.shishkin.itransition.mappers.FileToUriMapper
import com.shishkin.itransition.mappers.StringUriToBitmapMapper
import com.shishkin.itransition.processors.BitmapProcessor
import com.shishkin.itransition.processors.FileProcessor
import com.shishkin.itransition.processors.ReduceBitmapSizeStrategy
import com.shishkin.itransition.workers.ImageCompressionWorkerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkerFactoryModule {

    @Provides
    @Singleton
    fun workerFactory(
        stringUriToBitmapMapper: StringUriToBitmapMapper,
        bitmapProcessor: BitmapProcessor,
        reduceBitmapSizeStrategy: ReduceBitmapSizeStrategy,
        fileProcessor: FileProcessor,
        fileToUriMapper: FileToUriMapper
    ): WorkerFactory {
        return ImageCompressionWorkerFactory(
            stringUriToBitmapMapper,
            bitmapProcessor,
            reduceBitmapSizeStrategy,
            fileProcessor,
            fileToUriMapper
        )
    }
}