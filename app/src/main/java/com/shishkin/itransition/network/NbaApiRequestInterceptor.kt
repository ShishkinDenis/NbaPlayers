package com.shishkin.itransition.network

import okhttp3.Interceptor
import okhttp3.Response

private const val USER_AGENT = "User-Agent"
private const val APP = "App"
private const val X_RAPID_API_KEY_NAME = "x-rapidapi-key"
private const val X_RAPID_API_HOST_NAME = "x-rapidapi-host"
private const val X_RAPID_API_HOST_VALUE = "free-nba.p.rapidapi.com"

class NbaApiRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newRequest = original.newBuilder().header(USER_AGENT, APP)
            //     TODO Will be reworked - API key
            .header(X_RAPID_API_KEY_NAME, "6db3e9805dmsh48065f33193b2d0p1e1a19jsn8cc478ac8bdd")
            .header(X_RAPID_API_HOST_NAME, X_RAPID_API_HOST_VALUE)
            .method(original.method, original.body)
            .build()
        return chain.proceed(newRequest)
    }
}