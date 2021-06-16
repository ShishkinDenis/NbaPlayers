package com.shishkin.itransition


import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse

sealed class NbaPlayersUiState {
    object Loading : NbaPlayersUiState()
    data class Success(var nbaPlayers: RestResponse<List<NbaPlayer>>?) : NbaPlayersUiState()
    data class Error(var exception: Throwable) : NbaPlayersUiState()
    object Empty : NbaPlayersUiState()

}