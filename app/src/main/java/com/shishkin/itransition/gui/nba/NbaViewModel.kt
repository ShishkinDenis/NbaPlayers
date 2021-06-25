package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NbaViewModel @Inject constructor(var nbaRepository: NbaRepository) : ViewModel() {

    fun fetchPlayersPagination(): Flow<PagingData<ListItem>> {
        return nbaRepository.getNbaPlayersListPagination()
    }

//    fun fetchPlayersDb(): Flow<PagingData<ListItem>> {
//        return nbaRepository.getNbaPlayersListDb().cachedIn(viewModelScope)
//    }

    fun fetchPlayersDb(): Flow<PagingData<NbaPlayer>> {
        return nbaRepository.getNbaPlayersListDb().cachedIn(viewModelScope)
    }

}