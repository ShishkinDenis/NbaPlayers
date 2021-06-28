package com.shishkin.itransition.di

import androidx.paging.ExperimentalPagingApi
import com.shishkin.itransition.repository.DefaultNbaRepository
import com.shishkin.itransition.repository.NbaRepository
import dagger.Module
import dagger.Provides


@ExperimentalPagingApi
@Module
class RepositoryModule {
//    TODO for providing context for DB
//class RepositoryModule(val application: MyApplication) {
//    @Provides
//    @Singleton
//    fun providesContext(): Context = application.applicationContext
//    var nbaPlayerDataBase: NbaPlayerDataBase? =  NbaPlayerDataBase.getInstance(providesContext)
//    var nbaRepository: NbaRepository = DefaultNbaRepository(nbaPlayerDataBase)

    var nbaRepository: NbaRepository = DefaultNbaRepository()

    @Provides
    fun provideLocationRepository(): NbaRepository {
        return nbaRepository
    }
}