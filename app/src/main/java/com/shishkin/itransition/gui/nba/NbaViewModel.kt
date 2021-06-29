package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@InternalCoroutinesApi
class NbaViewModel @Inject constructor(var nbaRepository: NbaRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<NbaPlayersUiState> = MutableStateFlow(NbaPlayersUiState.Empty)
    val uiState: StateFlow<NbaPlayersUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = NbaPlayersUiState.Loading
            nbaRepository.getNbaPlayersList()
                    .catch { e -> _uiState.value = NbaPlayersUiState.Error(e) }
                    .collect { nbaPlayers ->
                        _uiState.value = NbaPlayersUiState.Success(nbaPlayers)
                    }
        }
    }

}