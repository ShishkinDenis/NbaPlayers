package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.Result
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

  private val _playersState: MutableStateFlow<Result<List<NbaPlayer>>> =
    MutableStateFlow(Result.loading())

  val playersState: StateFlow<Result<List<NbaPlayer>>> = _playersState

  init {
    loadPlayers()
  }

  private fun loadPlayers() {
    viewModelScope.launch {
      _playersState.value = Result.loading()
      nbaRepository.getNbaPlayersList()
        .catch { e -> _playersState.value = Result.error(e.message, e) }
        .collect { nbaPlayers ->
          nbaPlayers.fold(
            onSuccess = { list ->
              _playersState.value = Result.success(list)
            },
            onFailure = { error ->
              _playersState.value = Result.error(error.message, error)
            }
          )
        }
    }
  }
}