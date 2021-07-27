package com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ImagePickerSheetDialogFragmentModule {

    @ContributesAndroidInjector()
    abstract fun provideImagePickerSheetDialogFragment(): ImagePickerSheetDialogFragment
}