package com.shishkin.itransition.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.gui.games.NbaGamesPagingDataSource
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalPagingApi
class DefaultNbaRepository @Inject constructor(
  private val nbaPlayerDao: NbaPlayerDao,
  private val nbaApi: NbaApi
) : NbaRepository {

  override fun getNbaPlayersListDB(): Flow<KResult<List<NbaPlayer>>> {
    return flow {

      try {
        // Get data from API
        val apiData = nbaApi.getAllNbaPlayers()
        val apiList = apiData.data

        // If its empty, throw the exception
        if (apiList.isNullOrEmpty()) {
          throw IllegalStateException("Data not found exception")
        }

        nbaPlayerDao.insertAllPlayers(apiList)
        emit(KResult.success(apiList))

        // If DAO throws the error, then we have to handle it here to get the data from cache
      } catch (e: Exception) {
        // get cached data
        val cachedData = nbaPlayerDao.getAllPlayers()

        // if cached data is null, throw the failure exception
        if (cachedData.isNullOrEmpty()) {
          emit(KResult.failure(IllegalStateException("No data found, test message")))
        } else {
          // else submit the cached data
          emit(KResult.success(cachedData))
        }
      }
    }.flowOn(Dispatchers.IO)
  }

  override fun getSpecificPlayerDB(playerId: Int?): Flow<KResult<NbaPlayer?>> {
    return flow {
      val cashedData = nbaPlayerDao.getSpecificPlayer(playerId)
      emit(KResult.success(cashedData))

      val flowData = nbaApi.getSpecificPlayer(playerId)
      emit(Result.success(flowData))
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


