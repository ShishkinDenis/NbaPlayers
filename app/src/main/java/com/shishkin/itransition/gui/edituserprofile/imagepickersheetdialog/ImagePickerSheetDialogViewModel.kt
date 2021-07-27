package com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.shishkin.itransition.workers.*
import timber.log.Timber
import javax.inject.Inject

class ImagePickerSheetDialogViewModel @Inject constructor(private val workManager: WorkManager) :
    ViewModel() {

    //    TODO outputUri сделать shared flow на итоговый результат. На этот флоу мы подпишемся во фрагменте.
//    private val _outputUri = MutableSharedFlow<Uri>()
//    val outputUri = _outputUri.asSharedFlow()

//    private fun emitUri(outputUri: Uri) {
//        viewModelScope.launch {
//            _outputUri.emit(outputUri)
//        }
//    }

    val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(WORK_MANAGER_TAG_OUTPUT)
    }

    fun processWorkInfos(listOfWorkInfo: List<WorkInfo>) {
        val workInfo = listOfWorkInfo[0]
        if (listOfWorkInfo[0].state == WorkInfo.State.SUCCEEDED) {
            val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)
            Timber.tag("OUTPUT_URI").d("Observable" + outputImageUri.toString())
        }
    }

    fun compressImageWithWorker(inputImageUri: Uri) {
        val myWorkRequest = OneTimeWorkRequest
            .Builder(ImageCompressionWorker::class.java)
            .setInputData(
                createInputDataForUri(
                    OUTPUT_FILE_NAME, COMPRESSED_IMAGE_OUTPUT_PATH, inputImageUri,
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
            filePath: String,
            inputImageUri: Uri,
            imageQuality: Int
        ): Data {
            val builder = Data.Builder()
            inputImageUri.let {
                with(builder) {
                    putString(KEY_OUTPUT_FILE_NAME, fileName)
                    putString(KEY_COMPRESSED_IMAGE_OUTPUT_PATH, filePath)
                    putString(KEY_IMAGE_URI, inputImageUri.toString())
                    putInt(KEY_IMAGE_QUALITY, imageQuality)
                }
            }
            return builder.build()
        }
    }
}