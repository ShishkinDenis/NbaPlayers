package com.shishkin.itransition.network

import com.shishkin.itransition.network.entities.NbaConfiguration
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val USER_AGENT = "User-Agent"
private const val APP = "App"
private const val X_RAPID_API_KEY_NAME = "x-rapidapi-key"
private const val X_RAPID_API_HOST_NAME = "x-rapidapi-host"
private const val X_RAPID_API_HOST_VALUE = "free-nba.p.rapidapi.com"

class NbaApiRequestInterceptor @Inject constructor(private val nbaConfiguration: NbaConfiguration) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newRequest = original.newBuilder().header(USER_AGENT, APP)
            .header(X_RAPID_API_KEY_NAME, nbaConfiguration.nbaApiKey)
            .header(X_RAPID_API_HOST_NAME, X_RAPID_API_HOST_VALUE)
            .method(original.method, original.body)
            .build()
        return chain.proceed(newRequest)
    }
}

