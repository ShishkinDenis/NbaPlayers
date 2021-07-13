package com.shishkin.itransition.gui.userprofile

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserProfileFragmentModule {

    @ContributesAndroidInjector()
    abstract fun provideUserProfileFragment(): UserProfileFragment
}