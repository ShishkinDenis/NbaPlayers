package com.shishkin.itransition.repository

import androidx.paging.PagingData
import com.shishkin.itransition.db.PlayerWithTeam
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.KResult
import kotlinx.coroutines.flow.Flow

interface NbaRepository {

    fun getNbaPlayersListDB(): Flow<KResult<List<PlayerWithTeam>>>

    fun getSpecificPlayerDB(playerId: Int?): Flow<KResult<PlayerWithTeam?>>

    fun getNbaGamesListPagination(): Flow<PagingData<ListItem>?>
}