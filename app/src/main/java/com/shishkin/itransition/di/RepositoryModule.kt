package com.shishkin.itransition.di

import com.shishkin.itransition.repository.DefaultNbaRepository
import com.shishkin.itransition.repository.NbaRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    var nbaRepository: NbaRepository = DefaultNbaRepository()

    @Provides
    fun provideLocationRepository(): NbaRepository {
        return nbaRepository
    }
}