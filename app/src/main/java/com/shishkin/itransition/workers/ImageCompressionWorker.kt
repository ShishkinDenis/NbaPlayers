package com.shishkin.itransition.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog.COMPRESSED_IMAGE_OUTPUT_PATH
import com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog.KEY_IMAGE_URI
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

private const val COMPRESSED_IMAGE_NAME = "compressed-output-%s.jpg"

class ImageCompressionWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        return try {
            if (TextUtils.isEmpty(resourceUri)) {
                Timber.e(applicationContext.getString(R.string.image_compression_worker_invalid_input_uri))
                throw IllegalArgumentException(applicationContext.getString(R.string.image_compression_worker_invalid_input_uri))
            }
            val resolver = applicationContext.contentResolver
            val picture =
                BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            val outputUri = writeBitmapToFile(picture)
            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            return Result.success(outputData)
        } catch (throwable: Throwable) {
            Timber.e(
                throwable,
                applicationContext.getString(R.string.image_compression_worker_error_applying_compression)
            )
            Result.failure()
        }
    }

    private fun writeBitmapToFile(bitmap: Bitmap): Uri {
        val fileName = String.format(COMPRESSED_IMAGE_NAME, UUID.randomUUID().toString())
        val outputDir = File(applicationContext.filesDir, COMPRESSED_IMAGE_OUTPUT_PATH)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, fileName)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)
        } finally {
            out?.let { fileOutputStream ->
                try {
                    fileOutputStream.close()
                } catch (ignore: IOException) {
                    Timber.e(ignore)
                }
            }
        }
        return Uri.fromFile(outputFile)
    }
}
