package com.shishkin.itransition.di

import androidx.paging.ExperimentalPagingApi
import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.repository.DefaultNbaRepository
import com.shishkin.itransition.repository.NbaRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@ExperimentalPagingApi
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(): NbaRepository {
        return DefaultNbaRepository()
    }

}