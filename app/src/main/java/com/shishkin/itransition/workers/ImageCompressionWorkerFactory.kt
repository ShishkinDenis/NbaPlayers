package com.shishkin.itransition.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.shishkin.itransition.mappers.FileToUriMapper
import com.shishkin.itransition.mappers.StringUriToBitmapMapper
import com.shishkin.itransition.processors.BitmapProcessor
import com.shishkin.itransition.processors.FileProcessor
import com.shishkin.itransition.processors.ReduceBitmapSizeStrategy
import javax.inject.Inject

class ImageCompressionWorkerFactory @Inject constructor(
    private val stringUriToBitmapMapper: StringUriToBitmapMapper,
    private val bitmapProcessor: BitmapProcessor,
    private val reduceBitmapSizeStrategy: ReduceBitmapSizeStrategy,
    private val fileProcessor: FileProcessor,
    private val fileToUriMapper: FileToUriMapper
) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return ImageCompressionWorker(
            appContext,
            workerParameters,
            stringUriToBitmapMapper,
            bitmapProcessor,
            reduceBitmapSizeStrategy,
            fileProcessor,
            fileToUriMapper
        )
    }
}