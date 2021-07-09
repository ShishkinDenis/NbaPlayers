package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.gui.nba.mappers.PlayerWithTeamToNbaPlayerUiMapper
import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi
import com.shishkin.itransition.network.entities.ResultState
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NbaViewModel @Inject constructor(private val nbaRepository: NbaRepository) : ViewModel() {

    private val _playersState: MutableStateFlow<ResultState<List<NbaPlayerUi>>> =
        MutableStateFlow(ResultState.loading())

    val playersState: StateFlow<ResultState<List<NbaPlayerUi>>> = _playersState

    init {
        loadPlayers()
    }

    private fun loadPlayers() {
        viewModelScope.launch {
            _playersState.value = ResultState.loading()
            nbaRepository.getNbaPlayersListDB()
                .catch { e -> _playersState.value = ResultState.error(e.message, e) }
                .collect { nbaPlayers ->
                    nbaPlayers.fold(
                        onSuccess = { list ->
                            _playersState.value = ResultState.success(
                                PlayerWithTeamToNbaPlayerUiMapper().invoke(list)
                            )
                        },
                        onFailure = { error ->
                            _playersState.value = ResultState.error(error.message, error)
                        }
                    )
                }
        }

    }
}
