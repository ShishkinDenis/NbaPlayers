package com.shishkin.itransition.repository

import com.shishkin.itransition.db.NbaPlayerData
import kotlinx.coroutines.flow.Flow

interface NbaPlayerRepository {
    fun getNbaPlayersData(): Flow<NbaPlayerData?>
    fun getSpecificPlayer(playerId: Int)


    //    Coroutine + LiveData
//suspend fun getData() : Data?

//    Call
//    fun getNbaPlayersData()
}