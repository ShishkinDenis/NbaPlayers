package com.shishkin.itransition.di

import androidx.paging.ExperimentalPagingApi
import com.shishkin.itransition.repository.DefaultNbaRepository
import com.shishkin.itransition.repository.NbaRepository
import dagger.Module
import dagger.Provides


@ExperimentalPagingApi
@Module
class RepositoryModule {

    var nbaRepository: NbaRepository = DefaultNbaRepository()

    @Provides
    fun provideLocationRepository(): NbaRepository {
        return nbaRepository
    }
}