package com.shishkin.itransition.network

import com.shishkin.itransition.network.entities.NbaGame
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import com.shishkin.itransition.network.entities.RestResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaApi {

    @GET("players")
    suspend fun getAllNbaPlayers(): RestResponse<List<NbaPlayerRemote>>

    @GET("games")
    suspend fun getAllNbaGamesPagination(@Query("page") page: Int): RestResponse<List<NbaGame>>

    //    TODO поменять на Single?
    @GET("players")
    fun getAllNbaPlayersRX(): Observable<RestResponse<List<NbaPlayerRemote>>>
}