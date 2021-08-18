package com.shishkin.itransition.gui.login

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginFragmentModule {

    @ContributesAndroidInjector
    abstract fun provideLoginFragment(): LoginFragment
}
