package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.network.entities.NbaPlayer
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

  // TODO Evgeny: почему такой длинный пакет для Result?
    private val _playersState: MutableStateFlow<com.shishkin.itransition.network.entities.Result<List<NbaPlayer>>> =
            MutableStateFlow(com.shishkin.itransition.network.entities.Result.loading())

    val playersState: StateFlow<com.shishkin.itransition.network.entities.Result<List<NbaPlayer>>> = _playersState

    init {
        loadPlayers()
    }

    private fun loadPlayers() {
        viewModelScope.launch {
            _playersState.value = com.shishkin.itransition.network.entities.Result.loading()
            nbaRepository.getNbaPlayersListDB()
                    .catch { e -> _playersState.value = com.shishkin.itransition.network.entities.Result.error(e.message, e) }
                    .collect { nbaPlayers ->
                        nbaPlayers.fold(
                                onSuccess = { list ->
                                    _playersState.value = com.shishkin.itransition.network.entities.Result.success(list)
                                },
                                onFailure = { error ->
                                    _playersState.value = com.shishkin.itransition.network.entities.Result.error(error.message, error)
                                }
                        )
                    }
        }

    }
}