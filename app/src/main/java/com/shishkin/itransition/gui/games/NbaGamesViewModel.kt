package com.shishkin.itransition.gui.games

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NbaGamesViewModel @Inject constructor(var nbaRepository: NbaRepository) : ViewModel() {

    fun fetchGamesPagination(): Flow<PagingData<ListItem>?> {
        return nbaRepository.getNbaGamesListPagination()
    }
}