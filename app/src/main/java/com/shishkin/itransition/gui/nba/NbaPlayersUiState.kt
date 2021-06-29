package com.shishkin.itransition.gui.nba


import com.shishkin.itransition.network.entities.NbaGame
import com.shishkin.itransition.network.entities.RestResponse

sealed class NbaGamesUiState {
    object Loading : NbaGamesUiState()
    data class Success(var nbaGames: RestResponse<List<NbaGame>>?) : NbaGamesUiState()
    data class Error(var exception: Throwable) : NbaGamesUiState()
    object Empty : NbaGamesUiState()
}