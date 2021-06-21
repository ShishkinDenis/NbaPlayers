package com.shishkin.itransition.gui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.gui.nba.NbaGamesUiState
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NbaGamesViewModel @Inject constructor(var nbaRepository: NbaRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<NbaGamesUiState> =
        MutableStateFlow(NbaGamesUiState.Empty)
    val uiState: StateFlow<NbaGamesUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = NbaGamesUiState.Loading
            nbaRepository.getNbaGamesList()
                .catch { e -> _uiState.value = NbaGamesUiState.Error(e) }
                .collect { nbaGames ->
                    _uiState.value = NbaGamesUiState.Success(nbaGames)
                }
        }
    }
}