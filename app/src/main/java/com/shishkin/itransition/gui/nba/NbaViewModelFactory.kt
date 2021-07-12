package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.gui.nba.mappers.PlayerWithTeamToNbaPlayerUiMapper
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject

class NbaViewModelFactory @Inject constructor(
    private val nbaRepository: NbaRepository,
    private val playerWithTeamToNbaPlayerUiMapper: PlayerWithTeamToNbaPlayerUiMapper
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NbaViewModel(
            nbaRepository = nbaRepository,
            playerWithTeamToNbaPlayerUiMapper = playerWithTeamToNbaPlayerUiMapper
        ) as T
    }
}