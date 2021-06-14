package com.shishkin.itransition.di

import com.shishkin.itransition.repository.DefaultNbaPlayerRepository
import com.shishkin.itransition.repository.NbaPlayerRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    var nbaPlayerRepository: NbaPlayerRepository = DefaultNbaPlayerRepository()

    @Provides
    fun provideLocationRepository(): NbaPlayerRepository {
        return nbaPlayerRepository
    }
}