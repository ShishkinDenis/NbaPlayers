package com.shishkin.itransition.gui.edituserprofile

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EditUserProfileFragmentModule {

    @ContributesAndroidInjector()
    abstract fun provideEditUserProfileFragment(): EditUserProfileFragment
}