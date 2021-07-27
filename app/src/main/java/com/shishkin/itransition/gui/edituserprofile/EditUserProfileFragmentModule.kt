package com.shishkin.itransition.gui.edituserprofile

import com.shishkin.itransition.gui.edituserprofile.imagepickersheetdialog.ImagePickerSheetDialogFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EditUserProfileFragmentModule {

    @ContributesAndroidInjector(
        modules = [
            ImagePickerSheetDialogFragmentModule::class
        ]
    )
    abstract fun provideEditUserProfileFragment(): EditUserProfileFragment
}