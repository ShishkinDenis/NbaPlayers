package com.shishkin.itransition.network


import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaPlayerData
import com.shishkin.itransition.network.entities.RestResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NbaPlayersApi {

    @GET("players")
    suspend fun getAllNbaPlayers(): RestResponse<NbaPlayerData>

//    Coroutines + LiveData or Flow
//    @GET("players")
//    suspend fun getAllNbaPlayers(): NbaPlayerData


//    Call
//    @GET("players")
//    fun  getAllNbaPlayersUsingCall() : Call<NbaPlayerData>


    //    TODO change to Flow


    //    Call
    @GET("players/{id}")
    fun getSpecificPlayer(@Path("id") playerId: Int): Call<NbaPlayer>


    //    Call
//    @GET("players/{id}")
//    fun getSpecificPlayer(@Path("id") playerId: Int, @Query("rapidapi-key") apiKey: String): Call<NbaPlayer>
}