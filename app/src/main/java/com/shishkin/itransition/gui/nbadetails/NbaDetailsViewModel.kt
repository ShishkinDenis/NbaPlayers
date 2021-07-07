package com.shishkin.itransition.gui.nbadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.network.entities.NbaPlayer
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

    private val _specificPlayerState: MutableStateFlow<ResultState<NbaPlayer>> =
        MutableStateFlow(ResultState.loading())

    val specificPlayerState: StateFlow<ResultState<NbaPlayer>> = _specificPlayerState

    init {
        loadSpecificPlayer()
    }

    private fun loadSpecificPlayer() {
        viewModelScope.launch {
            _specificPlayerState.value = ResultState.loading()
            nbaRepository.getSpecificPlayerDB(nbaPlayerId)
                .catch { e ->
                    _specificPlayerState.value = ResultState.error(e.message, e)
                }
                .collect { nbaPlayers ->
                    nbaPlayers.fold(
                        onSuccess = { list ->
                            _specificPlayerState.value = ResultState.success(list)
                        },
                        onFailure = { error ->
                            _specificPlayerState.value = ResultState.error(error.message, error)
                        }
                    )
                }
        }
    }
}