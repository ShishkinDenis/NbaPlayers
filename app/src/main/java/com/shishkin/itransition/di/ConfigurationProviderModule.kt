package com.shishkin.itransition.di

import com.shishkin.itransition.JsonToNbaConfigurationConverter
import com.shishkin.itransition.network.entities.NbaConfiguration
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConfigurationProviderModule {

    @Provides
    @Singleton
    fun provideConfiguration(jsonToNbaConfigurationConverter: JsonToNbaConfigurationConverter): NbaConfiguration {
        return jsonToNbaConfigurationConverter.convertJsonToNbaConfiguration()
    }
}

