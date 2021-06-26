package com.shishkin.itransition.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shishkin.itransition.db.NbaPlayerDataBase
import com.shishkin.itransition.db.NbaPlayerMediator
import com.shishkin.itransition.gui.games.NbaGamesPagingDataSource
import com.shishkin.itransition.gui.nba.PlayersPagingDataSource
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.NbaApiClient
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ExperimentalPagingApi
class DefaultNbaRepository @Inject constructor() : NbaRepository {
//class DefaultNbaRepository @Inject constructor(var nbaPlayerDataBase: NbaPlayerDataBase?) : NbaRepository {

    //    TODO inject to constructor
    private val nbaApi: NbaApi =
        NbaApiClient.getClient().create(NbaApi::class.java)

//    TODO delete after injection to constructor
    lateinit var nbaPlayerDataBase: NbaPlayerDataBase
//   var nbaPlayerDataBase: NbaPlayerDataBase? = LocalInjector.injectDb()

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    override fun getNbaPlayersListPagination(): Flow<PagingData<ListItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                PlayersPagingDataSource(
                    nbaApi
                )
            }
        ).flow
    }

    override fun getNbaPlayersListDb(): Flow<PagingData<NbaPlayer>> {
        if (nbaPlayerDataBase == null) throw IllegalStateException("Database is not initialized")

        val pagingSourceFactory = { nbaPlayerDataBase!!.nbaPlayerDao().getAllPlayers() }
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true),
            remoteMediator =   NbaPlayerMediator(nbaApi, nbaPlayerDataBase!!),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getSpecificPlayer(playerId: Int?): Flow<NbaPlayer?> {
        return flow {
            val flowData = nbaApi?.getSpecificPlayer(playerId)
            emit(flowData)
        }.flowOn(Dispatchers.IO)
    }

    override fun getNbaGamesListPagination(): Flow<PagingData<ListItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                NbaGamesPagingDataSource(
                    nbaApi
                )
            }
        ).flow
    }
}