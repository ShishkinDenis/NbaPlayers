package com.shishkin.itransition.network

import com.shishkin.itransition.network.entities.Result
import retrofit2.Response


class NbaPlayersRemoteDataSource {

    private val nbaApi: NbaApi =
        NbaApiClient.getClient().create(NbaApi::class.java)

//    suspend fun fetchNbaPlayers() = getResult {nbaApi.getAllNbaPlayers()  }

     suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}