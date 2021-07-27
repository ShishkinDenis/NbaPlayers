package com.shishkin.itransition.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.shishkin.itransition.R
import com.shishkin.itransition.mappers.FileToUriMapper
import com.shishkin.itransition.mappers.StringUriToBitmapMapper
import com.shishkin.itransition.processors.BitmapProcessor
import com.shishkin.itransition.processors.FileProcessor
import com.shishkin.itransition.processors.ReduceBitmapSizeStrategy
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

private const val DEFAULT_QUALITY_VALUE = 0
const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"
const val KEY_IMAGE_QUALITY = "KEY_IMAGE_QUALITY"

class ImageCompressionWorker(
    context: Context,
    workerParams: WorkerParameters
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        val inputImageUri = inputData.getString(KEY_IMAGE_URI)
        val fileName = inputData.getString(KEY_OUTPUT_FILE_NAME)
        val imageQuality = inputData.getInt(KEY_IMAGE_QUALITY, DEFAULT_QUALITY_VALUE)
        try {
            val stringUriToBitmapMapper = StringUriToBitmapMapper()
            val convertedToBitmapPicture =
                inputImageUri?.let { stringUri ->
                    stringUriToBitmapMapper.convertUriToBitmap(
                        applicationContext.contentResolver,
                        stringUri
                    )
                }
            val bitmapProcessor = BitmapProcessor()
            val reduceBitmapSizeStrategy = ReduceBitmapSizeStrategy()
            val compressedBitmap =
                bitmapProcessor.processBitmap(convertedToBitmapPicture, reduceBitmapSizeStrategy)
            val fileProcessor = FileProcessor()
            val outputFile = compressedBitmap?.let { bitmap ->
                fileName?.let { fileName ->
                    fileProcessor.saveBitmapToFile(
                        applicationContext,
                        bitmap, fileName, imageQuality
                    )
                }
            }
            val fileToUriMapper = FileToUriMapper()
            val outputUri = outputFile?.let { file -> fileToUriMapper.convertFileToUri(file) }
            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            Result.success(outputData)
        } catch (throwable: Throwable) {
            Timber.e(
                throwable,
                applicationContext.getString(R.string.image_compression_worker_error_applying_compression)
            )
            Result.failure()
        }
    }
}
