package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shishkin.itransition.gui.nba.NbaPlayersUiState.*
import com.shishkin.itransition.network.entities.NbaPlayer

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

    fun fetchPlayersPagination(): Flow<PagingData<NbaPlayer>> {
        return nbaRepository.getNbaPlayersListPagination()
    }


//    private lateinit var _playersFlow: Flow<PagingData<NbaPlayer>>
//    val charactersFlow: Flow<PagingData<NbaPlayer>>
//        get() = _playersFlow
//
//    init {
//        fetchPlayersPagination()
//    }

//    private fun fetchPlayersPagination() = launchPagingAsync({
//        nbaRepository.getNbaPlayersListPagination().cachedIn(viewModelScope)
//    }, {
//        _playersFlow = it
//    })


}