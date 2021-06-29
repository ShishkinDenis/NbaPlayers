package com.shishkin.itransition.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.gui.games.NbaGamesPagingDataSource
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.NbaApiClient
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ExperimentalPagingApi
class DefaultNbaRepository @Inject constructor(val nbaPlayerDao: NbaPlayerDao) : NbaRepository {

    //    TODO inject to constructor
    private val nbaApi: NbaApi =
        NbaApiClient.getClient().create(NbaApi::class.java)

    override fun getNbaPlayersList(): Flow<KResult<List<NbaPlayer>>> {
        return flow {
            val flowData = nbaApi.getAllNbaPlayers()

            if (flowData.data == null) {
                emit(Result.failure(NullPointerException("No data found, test message")))
            } else {
                emit(Result.success(flowData.data))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getSpecificPlayer(playerId: Int?): Flow<KResult<NbaPlayer?>> {
        return flow {
            val flowData = nbaApi.getSpecificPlayer(playerId)
            emit(Result.success(flowData))
        }.flowOn(Dispatchers.IO)
    }



    override fun getNbaGamesListPagination(): Flow<PagingData<ListItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                NbaGamesPagingDataSource(
                    nbaApi)
            }
        ).flow
    }
}


