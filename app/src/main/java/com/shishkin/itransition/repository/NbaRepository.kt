package com.shishkin.itransition.repository


import androidx.paging.PagingData
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import kotlinx.coroutines.flow.Flow

interface NbaRepository {

    fun getNbaPlayersList(): Flow<KResult<List<NbaPlayer>>>

    fun getSpecificPlayer(playerId: Int?): Flow<KResult<NbaPlayer?>>

    fun getNbaGamesListPagination(): Flow<PagingData<ListItem>?>

}