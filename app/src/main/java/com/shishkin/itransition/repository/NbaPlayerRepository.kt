package com.shishkin.itransition.repository


import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface NbaPlayerRepository {

    fun getNbaPlayersData(): Flow<RestResponse<List<NbaPlayer>>?>

    fun getSpecificPlayer(playerId: Int)

}