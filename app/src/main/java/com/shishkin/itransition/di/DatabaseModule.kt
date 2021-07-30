package com.shishkin.itransition.di

import android.content.Context
import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.db.NbaPlayerDataBase
import com.shishkin.itransition.db.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

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
    fun provideUserDao(db: NbaPlayerDataBase): UserDao {
        return db.userDao()
    }
}