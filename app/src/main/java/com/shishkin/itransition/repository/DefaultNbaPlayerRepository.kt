package com.shishkin.itransition.repository

import com.shishkin.itransition.network.NbaPlayersApi
import com.shishkin.itransition.network.NbaPlayersApiClient
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DefaultNbaPlayerRepository @Inject constructor() : NbaPlayerRepository {
    private val nbaPlayersApi: NbaPlayersApi? =
        NbaPlayersApiClient.getClient().create(NbaPlayersApi::class.java)

    override fun getNbaPlayersData(): Flow<RestResponse<List<NbaPlayer>>?> {
        return flow {
            val flowData = nbaPlayersApi?.getAllNbaPlayers()
            emit(flowData)
        }.flowOn(Dispatchers.IO)
    }

    override fun getSpecificPlayer(playerId: Int): Flow<NbaPlayer?> {
        return flow {
            val flowData = nbaPlayersApi?.getSpecificPlayer(playerId)
            emit(flowData)
        }.flowOn(Dispatchers.IO)
    }
}