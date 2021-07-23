package com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.shishkin.itransition.workers.ImageCompressionWorker

class ImagePickerSheetDialogViewModel(application: Application) : AndroidViewModel(application) {

    internal var imageUri: Uri? = null
    internal var outputUri: Uri? = null
    private val workManager = WorkManager.getInstance(application)

    //    TODO переделать на flow
    internal val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(WORK_MANAGER_TAG_OUTPUT)
    }

    fun compressImageWithWorker() {
        val myWorkRequest = OneTimeWorkRequest
            .Builder(ImageCompressionWorker::class.java)
            .setInputData(createInputDataForUri())
            .addTag(WORK_MANAGER_TAG_OUTPUT)
            .build()
        workManager.enqueue(myWorkRequest)
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    internal fun setImageUri(uri: String?) {
        imageUri = uriOrNull(uri)
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }
}