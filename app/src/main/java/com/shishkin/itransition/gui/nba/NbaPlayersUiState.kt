package com.shishkin.itransition.gui.nba


import com.shishkin.itransition.network.entities.NbaGame
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.RestResponse

//TODO To merge to one class? Generics?

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

sealed class NbaGamesUiState {
    object Loading : NbaGamesUiState()
    data class Success(var nbaGames: RestResponse<List<NbaGame>>?) : NbaGamesUiState()
    data class Error(var exception: Throwable) : NbaGamesUiState()
    object Empty : NbaGamesUiState()
}