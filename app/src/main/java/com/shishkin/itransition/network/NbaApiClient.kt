package com.shishkin.itransition.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NbaApiClient {
    companion object {
        private var retrofit: Retrofit? = null
          // TODO Evgeny: Это все должно быть реализовано через Dagger 2. Удалить NbaApiClient
        fun getClient(): Retrofit {
            return if (retrofit == null) {

                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        val original = chain.request()

                        val newRequest = original.newBuilder()
                                .header("User-Agent", "App")
                          // TODO Evgeny: Хрантиь ключи приложениях запрещено. Покажу позже как это исправляется. Пока оставляй с пометкой TODO Will be reworked
                                .header(
                                        "x-rapidapi-key",
                                        "6db3e9805dmsh48065f33193b2d0p1e1a19jsn8cc478ac8bdd"
                                )
                                .header("x-rapidapi-host", "free-nba.p.rapidapi.com")
                                .method(original.method, original.body)
                                .build()

                        return chain.proceed(newRequest)
                    }
                })

                  // TODO Evgeny Интерсептор тоже будет провайдится через Dagger, чтобы иметь возможность
                // иметь логирование для debug, но не иметь для prod
                val httpInterceptor = HttpLoggingInterceptor()
                httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(httpInterceptor)

                val client = httpClient.build()

                  // TODO Evgeny: У тебя дважды дублируется free-nba.p.rapidapi.com", для всех таких текстов
                // создаются константы.
                retrofit = Retrofit.Builder()
                        .baseUrl("https://free-nba.p.rapidapi.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()

                retrofit!!
            } else {
                retrofit!!
            }
        }
    }

}

