package com.shishkin.itransition.repository

import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.NbaApiClient
import com.shishkin.itransition.network.entities.NbaGame
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DefaultNbaRepository @Inject constructor() : NbaRepository {
    private val nbaApi: NbaApi? =
        NbaApiClient.getClient().create(NbaApi::class.java)

    override fun getNbaPlayersList(): Flow<RestResponse<List<NbaPlayer>>?> {
        return flow {
            val flowData = nbaApi?.getAllNbaPlayers()
            emit(flowData)
        }.flowOn(Dispatchers.IO)
    }

    override fun getSpecificPlayer(playerId: Int?): Flow<NbaPlayer?> {
        return flow {
            val flowData = nbaApi?.getSpecificPlayer(playerId)
            emit(flowData)
        }.flowOn(Dispatchers.IO)
    }

    override fun getNbaGamesList(): Flow<RestResponse<List<NbaGame>>?> {
        return flow {
            val flowData = nbaApi?.getAllNbaGames()
            emit(flowData)
        }.flowOn(Dispatchers.IO)
    }
}