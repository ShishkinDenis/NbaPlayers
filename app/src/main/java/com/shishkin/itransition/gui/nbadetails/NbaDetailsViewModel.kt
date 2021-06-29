package com.shishkin.itransition.gui.nbadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.di.NbaPlayerId
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.Result
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class NbaDetailsViewModel @Inject constructor(
        var nbaRepository: NbaRepository,
        @NbaPlayerId var nbaPlayerId: Int?
) : ViewModel() {

    private val _specificPlayerState: MutableStateFlow<Result<NbaPlayer>> =
            MutableStateFlow(Result.loading())

    val specificPlayerState: StateFlow<Result<NbaPlayer>> = _specificPlayerState

    init {
        loadSpecificPlayer()
    }

    private fun loadSpecificPlayer() {
        viewModelScope.launch {
            _specificPlayerState.value = com.shishkin.itransition.network.entities.Result.loading()
            nbaRepository.getSpecificPlayerDB(nbaPlayerId)
                    .catch { e -> _specificPlayerState.value = com.shishkin.itransition.network.entities.Result.error(e.message, e) }
                    .collect { nbaPlayers ->
                        nbaPlayers.fold(
                                onSuccess = { list ->
                                    _specificPlayerState.value = com.shishkin.itransition.network.entities.Result.success(list)
                                },
                                onFailure = { error ->
                                    _specificPlayerState.value = com.shishkin.itransition.network.entities.Result.error(error.message, error)
                                }
                        )
                    }
        }

    }
}