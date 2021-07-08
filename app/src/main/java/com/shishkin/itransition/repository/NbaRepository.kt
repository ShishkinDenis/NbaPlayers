package com.shishkin.itransition.repository

import androidx.paging.PagingData
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import kotlinx.coroutines.flow.Flow

interface NbaRepository {

    fun getNbaPlayersListDB(): Flow<KResult<List<NbaPlayerRemote>>>

    fun getSpecificPlayerDB(playerId: Int?): Flow<KResult<NbaPlayerRemote?>>

    fun getNbaGamesListPagination(): Flow<PagingData<ListItem>?>
}