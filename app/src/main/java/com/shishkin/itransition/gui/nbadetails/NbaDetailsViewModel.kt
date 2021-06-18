package com.shishkin.itransition.gui.nbadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.di.NbaPlayerId
import com.shishkin.itransition.gui.nba.NbaPlayerUiState
import com.shishkin.itransition.repository.NbaPlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class NbaDetailsViewModel @Inject constructor(
    var nbaPlayerRepository: NbaPlayerRepository,
    @NbaPlayerId var nbaPlayerId: Int?
) : ViewModel() {

    private val _uiState: MutableStateFlow<NbaPlayerUiState> =
        MutableStateFlow(NbaPlayerUiState.Empty)
    val uiState: StateFlow<NbaPlayerUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = NbaPlayerUiState.Loading
            nbaPlayerRepository.getSpecificPlayer(nbaPlayerId)
                .catch { e -> _uiState.value = NbaPlayerUiState.Error(e) }
                .collect { nbaPlayer ->
                    _uiState.value = NbaPlayerUiState.Success(nbaPlayer)
                }
        }
    }
}