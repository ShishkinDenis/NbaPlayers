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
const val KEY_COMPRESSED_IMAGE_OUTPUT_PATH = "KEY_COMPRESSED_IMAGE_OUTPUT_PATH"
const val KEY_IMAGE_QUALITY = "KEY_IMAGE_QUALITY"

class ImageCompressionWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters
//    var stringUriToBitmapMapper: StringUriToBitmapMapper,
//    var bitmapProcessor: BitmapProcessor,
//    val reduceBitmapSizeStrategy: ReduceBitmapSizeStrategy,
//    var fileProcessor: FileProcessor,
//    var fileToUriMapper: FileToUriMapper
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
            val inputImageUri = inputData.getString(KEY_IMAGE_URI)
            val fileName = inputData.getString(KEY_OUTPUT_FILE_NAME)
            val filePath = inputData.getString(KEY_COMPRESSED_IMAGE_OUTPUT_PATH)
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

//            TODO use di
                val bitmapProcessor = BitmapProcessor()

                val reduceBitmapSizeStrategy = ReduceBitmapSizeStrategy()
                val compressedBitmap =
                    bitmapProcessor.processBitmap(convertedToBitmapPicture, reduceBitmapSizeStrategy)

                val fileProcessor = FileProcessor()

                val outputFile = compressedBitmap?.let { bitmap ->
                    fileName?.let { fileName ->
                        filePath?.let { filePath ->
                            fileProcessor.saveBitmapToFile(
                              applicationContext,
                              bitmap, fileName, filePath, imageQuality
                            )
                        }
                    }
                }

                val fileToUriMapper = FileToUriMapper()

                val outputUri = outputFile?.let { file -> fileToUriMapper.convertFileToUri(file) }
                val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
                Timber.tag("OUTPUT_URI").d("From Worker" + outputData.toString())
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
