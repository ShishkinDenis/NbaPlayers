package com.shishkin.itransition.repository


import androidx.paging.PagingData
import com.shishkin.itransition.network.entities.NbaGame
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse
import kotlinx.coroutines.flow.Flow

interface NbaRepository {

    fun getNbaPlayersList(): Flow<RestResponse<List<NbaPlayer>>?>

    fun getNbaPlayersListPagination(): Flow<PagingData<NbaPlayer>>

    fun getSpecificPlayer(playerId: Int?): Flow<NbaPlayer?>

    fun getNbaGamesList(): Flow<RestResponse<List<NbaGame>>?>

}