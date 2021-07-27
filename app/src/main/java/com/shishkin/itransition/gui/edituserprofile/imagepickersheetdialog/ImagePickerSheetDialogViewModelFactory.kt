package com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import javax.inject.Inject

class ImagePickerSheetDialogViewModelFactory @Inject constructor(
    private val workManager: WorkManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImagePickerSheetDialogViewModel(
            workManager = workManager
        ) as T
    }
}