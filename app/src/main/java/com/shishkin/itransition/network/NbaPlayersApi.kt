package com.shishkin.itransition.network


import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface NbaPlayersApi {

    @GET("players")
    suspend fun getAllNbaPlayers(): RestResponse<List<NbaPlayer>>

    @GET("/players/{id}")
    suspend fun getSpecificPlayer(@Path("id") playerId: Int?): NbaPlayer

}