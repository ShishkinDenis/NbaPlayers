package com.shishkin.itransition.gui.nba


import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse

//TODO объединить. Generics?

sealed class NbaPlayersUiState {
    object Loading : NbaPlayersUiState()
    data class Success(var nbaPlayers: RestResponse<List<NbaPlayer>>?) : NbaPlayersUiState()
    data class Error(var exception: Throwable) : NbaPlayersUiState()
    object Empty : NbaPlayersUiState()
}

sealed class NbaPlayerUiState {
    object Loading : NbaPlayerUiState()
    data class Success(var nbaPlayer: NbaPlayer?) : NbaPlayerUiState()
    data class Error(var exception: Throwable) : NbaPlayerUiState()
    object Empty : NbaPlayerUiState()

}