package com.shishkin.itransition.di

import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.NbaApiRequestInterceptor
import com.shishkin.itransition.network.entities.NbaConfiguration
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val URL = "https://free-nba.p.rapidapi.com"

@Module
class ApiServiceModule {

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun provideBaseRequestInterceptor(nbaConfiguration: NbaConfiguration?): NbaApiRequestInterceptor {
        return NbaApiRequestInterceptor(nbaConfiguration)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        nbaApiRequestInterceptor: NbaApiRequestInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(nbaApiRequestInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideNbaApi(retrofit: Retrofit): NbaApi {
        return retrofit.create(NbaApi::class.java)
    }
}