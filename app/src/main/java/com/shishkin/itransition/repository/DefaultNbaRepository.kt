package com.shishkin.itransition.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.db.toNbaPlayerLocalList
import com.shishkin.itransition.db.toNbaPlayerRemote
import com.shishkin.itransition.db.toNbaPlayerRemoteList
import com.shishkin.itransition.gui.games.NbaGamesPagingDataSource
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultNbaRepository @Inject constructor(
    private val nbaPlayerDao: NbaPlayerDao,
    private val nbaApi: NbaApi?
) : NbaRepository {

    override fun getNbaPlayersListDB(): Flow<KResult<List<NbaPlayerRemote>>> {
        return flow {
            try {
                val apiData = nbaApi?.getAllNbaPlayers()
                val apiList = apiData?.data

                val nbaPlayerLocalList = apiList?.toNbaPlayerLocalList()

                if (apiList.isNullOrEmpty()) {
                    throw IllegalStateException("Data not found exception")
                }
                nbaPlayerLocalList?.let { nbaPlayerDao.insertAllPlayers(it) }
                emit(KResult.success(apiList))
            } catch (e: Exception) {
                val cachedData = nbaPlayerDao.getAllPlayers()
                if (cachedData.isNullOrEmpty()) {
                    emit(KResult.failure(IllegalStateException("Data not found exception")))
                } else {
                    emit(KResult.success(cachedData.toNbaPlayerRemoteList()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getSpecificPlayerDB(playerId: Int?): Flow<KResult<NbaPlayerRemote?>> {
        return flow {
            try {
                val flowData = nbaApi?.getSpecificPlayer(playerId)
                emit(Result.success(flowData))
            } catch (e: Exception) {
                val cashedData = nbaPlayerDao.getSpecificPlayer(playerId)
                emit(KResult.success(cashedData.toNbaPlayerRemote()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getNbaGamesListPagination(): Flow<PagingData<ListItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                NbaGamesPagingDataSource(nbaApi)
            }
        ).flow
    }
}


