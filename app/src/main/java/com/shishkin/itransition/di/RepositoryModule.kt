package com.shishkin.itransition.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.db.NbaPlayerDataBase
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.NbaApiClient
import com.shishkin.itransition.repository.DefaultNbaRepository
import com.shishkin.itransition.repository.NbaRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDatabase(@Application context: Context): NbaPlayerDataBase {
        return NbaPlayerDataBase.buildDatabase(context)
    }

    @Singleton
    @Provides
    fun provideNbaDao(db: NbaPlayerDataBase): NbaPlayerDao {
        return db.nbaPlayerDao()
    }

    @Singleton
    @Provides
    fun provideNbaApi(): NbaApi {
        return NbaApiClient.getClient().create(NbaApi::class.java)
    }

    @ExperimentalPagingApi
    @Provides
    fun provideLocationRepository(nbaPlayerDao: NbaPlayerDao, nbaApi: NbaApi): NbaRepository {
        return DefaultNbaRepository(nbaPlayerDao, nbaApi)
    }

}