package com.shishkin.itransition.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.db.PlayerWithTeam
import com.shishkin.itransition.gui.games.NbaGamesPagingDataSource
import com.shishkin.itransition.gui.nba.mappers.NbaPlayerRemoteToLocalMapper
import com.shishkin.itransition.gui.nba.mappers.NbaPlayerRemoteToNbaTeamLocalMapper
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultNbaRepository @Inject constructor(
    private val nbaPlayerDao: NbaPlayerDao,
    private val nbaApi: NbaApi?,
    private val nbaPlayerRemoteToNbaTeamLocalMapper: NbaPlayerRemoteToNbaTeamLocalMapper,
    private val nbaPlayerRemoteToLocalMapper: NbaPlayerRemoteToLocalMapper
) : NbaRepository {

    override fun getNbaPlayersList(): Flow<KResult<List<PlayerWithTeam>>> {
        return flow<KResult<List<PlayerWithTeam>>> {
            try {
                val apiData = nbaApi?.getAllNbaPlayers()
                val apiList = apiData?.data
                val nbaPlayerLocalList = apiList?.let { nbaPlayerRemoteToLocalMapper.invoke(it) }
                val nbaTeamLocalList = apiList?.let { list ->
                    nbaPlayerRemoteToNbaTeamLocalMapper.invoke(list)
                }

                if (apiList.isNullOrEmpty()) {
                    throw IllegalStateException("Data not found exception")
                }
                nbaPlayerLocalList?.let { nbaPlayerDao.insertAllPlayers(it) }
                nbaTeamLocalList?.let { nbaPlayerDao.insertAllTeams(it) }
                val cachedData = nbaPlayerDao.getPlayersWithTeams()
                emit(KResult.success(cachedData))
            } catch (e: Exception) {
                val cachedData = nbaPlayerDao.getPlayersWithTeams()
                if (cachedData.isNullOrEmpty()) {
                    emit(KResult.failure(IllegalStateException("Data not found exception")))
                } else {
                    emit(KResult.success(cachedData))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getSpecificPlayer(playerId: Int?): Flow<KResult<PlayerWithTeam?>> {
        return flow {
            val cashedData = nbaPlayerDao.getSpecificPlayer(playerId)
            emit(KResult.success(cashedData))
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

    // TODO обработка failure случая
    //  TODO работа с БД
    override fun getNbaPlayersListRX(): Observable<Result<List<NbaPlayerRemote>?>>? {
        return nbaApi?.getAllNbaPlayersRX()
            ?.map {
                Result.success(it.data)
            }
            ?.subscribeOn(Schedulers.io())
    }
}


