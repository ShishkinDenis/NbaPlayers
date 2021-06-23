package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.shishkin.itransition.gui.nba.NbaPlayersUiState.*
import com.shishkin.itransition.gui.nba.lists.ListItem
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class NbaViewModel @Inject constructor(var nbaRepository: NbaRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<NbaPlayersUiState> = MutableStateFlow(Empty)
    val uiState: StateFlow<NbaPlayersUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = Loading
            nbaRepository.getNbaPlayersList()
                .catch { e -> _uiState.value = Error(e) }
                .collect { nbaPlayers ->
                    _uiState.value = Success(nbaPlayers)
                }
        }
    }

    fun fetchPlayersPagination(): Flow<PagingData<ListItem>> {
        return nbaRepository.getNbaPlayersListPagination()
    }

}