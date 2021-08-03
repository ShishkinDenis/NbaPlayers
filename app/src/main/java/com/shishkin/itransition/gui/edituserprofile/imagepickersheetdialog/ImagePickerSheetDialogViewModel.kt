package com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.shishkin.itransition.workers.ImageCompressionWorker
import com.shishkin.itransition.workers.KEY_IMAGE_QUALITY
import com.shishkin.itransition.workers.KEY_IMAGE_URI
import com.shishkin.itransition.workers.KEY_OUTPUT_FILE_NAME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagePickerSheetDialogViewModel @Inject constructor(private val workManager: WorkManager) :
    ViewModel() {

    private val outputUriData = MutableStateFlow<Uri?>(null)
    val outputUri = outputUriData.asStateFlow()

    val outputWorkInfos: LiveData<List<WorkInfo>> =
        workManager.getWorkInfosByTagLiveData(WORK_MANAGER_TAG_OUTPUT)

    fun processWorkInfos(listOfWorkInfo: List<WorkInfo>) {
        viewModelScope.launch {
            if (listOfWorkInfo.isNotEmpty()) {
                val info = listOfWorkInfo.first()

                if (info.state == WorkInfo.State.SUCCEEDED) {
                    val outputImageUri = info.outputData.getString(KEY_IMAGE_URI)
                    if (!outputImageUri.isNullOrEmpty()) {
                        outputUriData.emit(Uri.parse(outputImageUri))
                    }
                    workManager.pruneWork()
                }
            }
        }
    }

    fun compressImageWithWorker(inputImageUri: Uri) {
        val myWorkRequest = OneTimeWorkRequest
            .Builder(ImageCompressionWorker::class.java)
            .setInputData(
                createInputDataForUri(
                    OUTPUT_FILE_NAME, inputImageUri,
                    IMAGE_QUALITY
                )
            )
            .addTag(WORK_MANAGER_TAG_OUTPUT)
            .build()
        workManager.enqueue(myWorkRequest)
    }

    companion object {
        private fun createInputDataForUri(
            fileName: String,
            inputImageUri: Uri,
            imageQuality: Int
        ): Data {
            val builder = Data.Builder()
            inputImageUri.let {
                with(builder) {
                    putString(KEY_OUTPUT_FILE_NAME, fileName)
                    putString(KEY_IMAGE_URI, inputImageUri.toString())
                    putInt(KEY_IMAGE_QUALITY, imageQuality)
                }
            }
            return builder.build()
        }
    }
}