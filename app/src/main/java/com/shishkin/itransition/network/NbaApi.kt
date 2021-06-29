package com.shishkin.itransition.network


import com.shishkin.itransition.network.entities.NbaGame
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NbaApi {

    @GET("players")
    suspend fun getAllNbaPlayers(): RestResponse<List<NbaPlayer>>

    @GET("/players/{id}")
    suspend fun getSpecificPlayer(@Path("id") playerId: Int?): NbaPlayer

    @GET("games")
    suspend fun getAllNbaGamesPagination(@Query("page") page: Int): RestResponse<List<NbaGame>>


}