package com.shishkin.itransition.repository


import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaPlayerData
import com.shishkin.itransition.network.entities.RestResponse
import kotlinx.coroutines.flow.Flow

interface NbaPlayerRepository {

    fun getNbaPlayersData(): Flow<RestResponse<NbaPlayerData>?>



//    StateFlow
//    fun getNbaPlayersData(): Flow<NbaPlayerData>

    fun getSpecificPlayer(playerId: Int)


    //    Coroutine + LiveData
//suspend fun getData() : Data?

//    Call
//    fun getNbaPlayersData()
}