package com.shishkin.itransition.repository


import androidx.paging.PagingData
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import kotlinx.coroutines.flow.Flow

interface NbaRepository {

    fun getNbaPlayersListDB(): Flow<KResult<List<NbaPlayer>>>
    // TODO Evgeny закоменченный код
//    fun getNbaPlayersListDB(): Flow<Result<List<NbaPlayer>?>>

    fun getSpecificPlayerDB(playerId: Int?): Flow<KResult<NbaPlayer?>>

    fun getNbaGamesListPagination(): Flow<PagingData<ListItem>?>

}