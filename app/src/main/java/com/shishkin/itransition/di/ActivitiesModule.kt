package com.shishkin.itransition.di

import androidx.paging.ExperimentalPagingApi
import com.shishkin.itransition.gui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.InternalCoroutinesApi

/*
TODO Evgeny: очень много где у нас таких эксперементальных аннотаций. А давай их вырубим, чтобы студия
не просила их использовать.

Подсказка: в build.gradle есть kotlinOptions -> freeCompilerArgs. Погугли
 */

@ExperimentalPagingApi
@InternalCoroutinesApi
@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(
            modules = [
                FragmentsModule::class
            ]
    )
    abstract fun provideMainActivity(): MainActivity
}

