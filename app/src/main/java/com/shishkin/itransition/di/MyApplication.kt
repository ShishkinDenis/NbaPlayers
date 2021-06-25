package com.shishkin.itransition.di


import androidx.paging.ExperimentalPagingApi
import com.shishkin.itransition.db.LocalInjector
import com.shishkin.itransition.db.NbaPlayerDataBase
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalPagingApi
class MyApplication : DaggerApplication() {

    @InternalCoroutinesApi
    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent
            .builder()
            .create(this)

    override fun onCreate() {
        super.onCreate()
        LocalInjector.nbaPlayerDataBase = NbaPlayerDataBase.getInstance(this@MyApplication)
    }


}
