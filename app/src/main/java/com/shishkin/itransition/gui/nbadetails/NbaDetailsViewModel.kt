package com.shishkin.itransition.gui.nbadetails

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

class NbaDetailsViewModel @Inject constructor(
    private val nbaRepository: NbaRepository,
    @NbaPlayerId var nbaPlayerId: Int?
) : ViewModel() {

    private val _specificPlayerRemoteState: MutableStateFlow<ResultState<NbaPlayerUi>> =
        MutableStateFlow(ResultState.loading())

    val specificPlayerRemoteState: StateFlow<ResultState<NbaPlayerUi>> =
        _specificPlayerRemoteState

    init {
        loadSpecificPlayer()
    }

    private fun loadSpecificPlayer() {
        viewModelScope.launch {
            _specificPlayerRemoteState.value = ResultState.loading()
            nbaRepository.getSpecificPlayerDB(nbaPlayerId)
                .catch { e ->
                    _specificPlayerRemoteState.value = ResultState.error(e.message, e)
                }
                .collect { nbaPlayers ->
                    nbaPlayers.fold(
                        onSuccess = { list ->
                            _specificPlayerRemoteState.value = ResultState.success(
                                list?.let {
                                    PlayerWithTeamToNbaPlayerUiMapper()
                                        .mapFromPlayerWithTeamToNbaPlayerUi(it)
                                }
                            )
                        },
                        onFailure = { error ->
                            _specificPlayerRemoteState.value =
                                ResultState.error(error.message, error)
                        }
                    )
                }
        }
    }
}