package com.shishkin.itransition.gui.nba


import com.shishkin.itransition.network.entities.NbaPlayer

sealed class NbaPlayerUiState {
    object Loading : NbaPlayerUiState()
    data class Success(var nbaPlayer: NbaPlayer?) : NbaPlayerUiState()
    data class Error(var exception: Throwable) : NbaPlayerUiState()
    object Empty : NbaPlayerUiState()
}