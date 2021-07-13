package com.shishkin.itransition.di

import com.google.gson.Gson
import com.shishkin.itransition.R
import com.shishkin.itransition.network.RawFileReader
import com.shishkin.itransition.network.entities.NbaConfiguration
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConfigurationProviderModule {

    @Provides
    @Singleton
    fun provideConfiguration(rawFileReader: RawFileReader): NbaConfiguration? {
        val config = rawFileReader.loadJSONFromRaw(R.raw.nba_configuration)
        config.let {
            return Gson().fromJson(config, NbaConfiguration::class.java)
        }
    }
}

