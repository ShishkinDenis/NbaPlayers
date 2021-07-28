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
import javax.inject.Inject

private const val DEFAULT_QUALITY_VALUE = 0
const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"
const val KEY_IMAGE_QUALITY = "KEY_IMAGE_QUALITY"

class ImageCompressionWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val stringUriToBitmapMapper: StringUriToBitmapMapper,
    private val bitmapProcessor: BitmapProcessor,
    private val reduceBitmapSizeStrategy: ReduceBitmapSizeStrategy,
    private val fileProcessor: FileProcessor,
    private val fileToUriMapper: FileToUriMapper
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        Timber.tag("TestLogger").d("This is ImageCompressionWorker")
        val inputImageUri = inputData.getString(KEY_IMAGE_URI)
        val fileName = inputData.getString(KEY_OUTPUT_FILE_NAME)
        val imageQuality = inputData.getInt(KEY_IMAGE_QUALITY, DEFAULT_QUALITY_VALUE)
        try {
            val convertedToBitmapPicture =
                inputImageUri?.let { stringUri ->
                    stringUriToBitmapMapper.convertUriToBitmap(
                        applicationContext.contentResolver,
                        stringUri
                    )
                }
            val compressedBitmap =
                bitmapProcessor.processBitmap(convertedToBitmapPicture, reduceBitmapSizeStrategy)
            val outputFile = compressedBitmap?.let { bitmap ->
                fileName?.let { fileName ->
                    fileProcessor.saveBitmapToFile(
                        applicationContext,
                        bitmap, fileName, imageQuality
                    )
                }
            }
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
