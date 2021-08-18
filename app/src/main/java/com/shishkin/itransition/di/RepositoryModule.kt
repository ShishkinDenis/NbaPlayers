package com.shishkin.itransition.di

import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.db.UserDao
import com.shishkin.itransition.gui.nba.mappers.NbaPlayerRemoteToLocalMapper
import com.shishkin.itransition.gui.nba.mappers.NbaPlayerRemoteToNbaTeamLocalMapper
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.repository.*
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideLocationRepository(
        nbaPlayerDao: NbaPlayerDao,
        nbaApi: NbaApi,
        nbaPlayerRemoteToNbaTeamLocalMapper: NbaPlayerRemoteToNbaTeamLocalMapper,
        nbaPlayerRemoteToLocalMapper: NbaPlayerRemoteToLocalMapper
    ): NbaRepository {
        return DefaultNbaRepository(
            nbaPlayerDao, nbaApi, nbaPlayerRemoteToNbaTeamLocalMapper,
            nbaPlayerRemoteToLocalMapper
        )
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return DefaultUserRepository(userDao)
    }

    @Provides
    fun provideLoginRepository(): LoginRepository {
        return DefaultLoginRepository()
    }
}