package com.shishkin.itransition.repository

import androidx.paging.PagingData
import com.shishkin.itransition.db.PlayerWithTeam
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface NbaRepository {

    fun getNbaPlayersList(): Flow<KResult<List<PlayerWithTeam>>>

    fun getSpecificPlayer(playerId: Int?): Flow<KResult<PlayerWithTeam?>>

    fun getNbaGamesListPagination(): Flow<PagingData<ListItem>?>

    //    TODO переделать на PlayerWithTeam
    fun getNbaPlayersListRX(): Single<Result<List<NbaPlayerRemote>>>
}