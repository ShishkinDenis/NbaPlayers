package com.shishkin.itransition.repository


import androidx.paging.PagingData
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import kotlinx.coroutines.flow.Flow

interface NbaRepository {

    fun getNbaPlayersListPagination(): Flow<PagingData<ListItem>>

    fun getSpecificPlayer(playerId: Int?): Flow<NbaPlayer?>

    fun getNbaGamesListPagination(): Flow<PagingData<ListItem>?>

//TODO for MultiType

//    fun getNbaPlayersListDb(): Flow<PagingData<ListItem>>
    fun getNbaPlayersListDb(): Flow<PagingData<NbaPlayer>>
}