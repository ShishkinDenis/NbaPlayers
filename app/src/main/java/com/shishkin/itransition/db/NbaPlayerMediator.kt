package com.shishkin.itransition.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shishkin.itransition.gui.nba.PlayersPagingDataSource
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class NbaPlayerMediator(val apiService: NbaApi,val nbaPlayerDataBase: NbaPlayerDataBase) : RemoteMediator<Int, NbaPlayer>() {
//class NbaPlayerMediator(val apiService: NbaApi,val nbaPlayerDataBase: NbaPlayerDataBase) : RemoteMediator<Int, ListItem>() {
//    TODO перенести в репозиторий?
     val DEFAULT_PAGE_INDEX = 1

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, NbaPlayer>): MediatorResult {
//        loadType: LoadType, state: PagingState<Int, ListItem>): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response =apiService.getAllNbaPlayersPagination(page).data
//            val convertedResponse = response?.let { convertList(it) }
//            val isEndOfList = convertedResponse!!.isEmpty()
            val isEndOfList = response?.isEmpty()

            nbaPlayerDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    nbaPlayerDataBase.remoteKeysDao().clearRemoteKeys()
                    nbaPlayerDataBase.nbaPlayerDao().clearAllPlayers()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList!!) null else page + 1
//                val keys = convertedResponse.map {
                val keys = response?.map {
//                    RemoteKeys(id = (it.item as NbaPlayer).id, prevKey = prevKey, nextKey = nextKey)
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                keys?.let { nbaPlayerDataBase.remoteKeysDao().insertAll(it) }
                response?.let { nbaPlayerDataBase.nbaPlayerDao().insertAllPlayers(it) }
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList!!)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, NbaPlayer>): RemoteKeys? {
//    private suspend fun getFirstRemoteKey(state: PagingState<Int, ListItem>): RemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
//            ?.let { nbaPlayer -> nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId((nbaPlayer.item as NbaPlayer).id)}
            ?.let { nbaPlayer -> nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId(nbaPlayer.id)}
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int,  NbaPlayer>): RemoteKeys? {
//    private suspend fun getLastRemoteKey(state: PagingState<Int,  ListItem>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { nbaPlayer -> nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId(nbaPlayer.id)}
//            ?.let { nbaPlayer -> nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId((nbaPlayer.item as NbaPlayer).id)}
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, NbaPlayer>): RemoteKeys? {
//    private suspend fun getClosestRemoteKey(state: PagingState<Int, ListItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
//            (state.closestItemToPosition(position)?.item as NbaPlayer).id.let { id ->
                nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId(id)
            }
        }
    }

    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, NbaPlayer>): Any? {
//    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, ListItem>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                remoteKeys.prevKey ?: return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    private fun convertList(listOfNbaPlayers: List<NbaPlayer>): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
        for (item in listOfNbaPlayers) {
            val nbaPlayerListItem = ListItem(item, PlayersPagingDataSource.VIEW_TYPE_NBA_PLAYER)
            val nbaTeamListItem = ListItem(item.team, PlayersPagingDataSource.VIEW_TYPE_NBA_TEAM)
            listOfListItem.add(nbaPlayerListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }

}

/*
@ExperimentalPagingApi
//class NbaPlayerMediator(val apiService: NbaApi,val nbaPlayerDataBase: NbaPlayerDataBase) : RemoteMediator<Int, NbaPlayer>() {
class NbaPlayerMediator(val apiService: NbaApi,val nbaPlayerDataBase: NbaPlayerDataBase) : RemoteMediator<Int, ListItem>() {
    //    TODO перенести в репозиторий?
    val DEFAULT_PAGE_INDEX = 1

    override suspend fun load(
//        loadType: LoadType, state: PagingState<Int, NbaPlayer>): MediatorResult {
        loadType: LoadType, state: PagingState<Int, ListItem>): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response =apiService.getAllNbaPlayersPagination(page).data
            val convertedResponse = response?.let { convertList(it) }
            val isEndOfList = convertedResponse!!.isEmpty()

            nbaPlayerDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    nbaPlayerDataBase.remoteKeysDao().clearRemoteKeys()
                    nbaPlayerDataBase.nbaPlayerDao().clearAllPlayers()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = convertedResponse.map {
                    RemoteKeys(id = (it.item as NbaPlayer).id, prevKey = prevKey, nextKey = nextKey)
                }
                nbaPlayerDataBase.remoteKeysDao().insertAll(keys)
                nbaPlayerDataBase.nbaPlayerDao().insertAllPlayers(response)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    //    private suspend fun getFirstRemoteKey(state: PagingState<Int, NbaPlayer>): RemoteKeys? {
    private suspend fun getFirstRemoteKey(state: PagingState<Int, ListItem>): RemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { nbaPlayer -> nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId((nbaPlayer.item as NbaPlayer).id)}
    }

    //    private suspend fun getLastRemoteKey(state: PagingState<Int,  NbaPlayer>): RemoteKeys? {
    private suspend fun getLastRemoteKey(state: PagingState<Int,  ListItem>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
//            ?.let { nbaPlayer -> nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId(nbaPlayer.id)}
            ?.let { nbaPlayer -> nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId((nbaPlayer.item as NbaPlayer).id)}
    }

    //    private suspend fun getClosestRemoteKey(state: PagingState<Int, NbaPlayer>): RemoteKeys? {
    private suspend fun getClosestRemoteKey(state: PagingState<Int, ListItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { repoId ->
            (state.closestItemToPosition(position)?.item as NbaPlayer).id.let { id ->
                nbaPlayerDataBase.remoteKeysDao().remoteKeysPlayerId(id)
            }
        }
    }

    //    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, NbaPlayer>): Any? {
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, ListItem>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                remoteKeys.prevKey ?: return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    private fun convertList(listOfNbaPlayers: List<NbaPlayer>): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
        for (item in listOfNbaPlayers) {
            val nbaPlayerListItem = ListItem(item, PlayersPagingDataSource.VIEW_TYPE_NBA_PLAYER)
            val nbaTeamListItem = ListItem(item.team, PlayersPagingDataSource.VIEW_TYPE_NBA_TEAM)
            listOfListItem.add(nbaPlayerListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }

}*/

