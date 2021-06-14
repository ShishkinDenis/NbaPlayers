package com.shishkin.itransition.network

import com.shishkin.itransition.db.NbaPlayerData
import com.shishkin.itransition.db.NbaPlayer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NbaPlayersApi {

    //    TODO change to Flow
//    Coroutines + LiveData
    @GET("players")
    suspend fun getAllNbaPlayers(@Query("rapidapi-key") apiKey: String): NbaPlayerData


//    Call
    @GET("players")
    fun  getAllNbaPlayersUsingCall(@Query("rapidapi-key") apiKey : String) : Call<NbaPlayerData>


    //    TODO change to Flow
//    Call
    @GET("players/{id}")
    fun getSpecificPlayer(@Path("id") playerId: Int, @Query("rapidapi-key") apiKey: String): Call<NbaPlayer>

}