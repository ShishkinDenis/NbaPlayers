package com.shishkin.itransition.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NbaPlayersApiClient {

    //    TODO Use dagger
    companion object {
        val BASE_URL = "https://free-nba.p.rapidapi.com/"
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
    }
}