package com.shishkin.itransition

import com.shishkin.itransition.db.NbaPlayerData

sealed class NbaPlayersUiState {
    data class Success(var nbaPlayers: NbaPlayerData?) : NbaPlayersUiState()
    data class Error(var exception: Throwable) : NbaPlayersUiState()

}