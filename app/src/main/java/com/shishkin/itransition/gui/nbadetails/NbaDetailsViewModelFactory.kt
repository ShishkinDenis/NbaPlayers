package com.shishkin.itransition.gui.nbadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.gui.nba.mappers.PlayerWithTeamToNbaPlayerUiMapper
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject

class NbaDetailsViewModelFactory @Inject constructor(
    private val nbaRepository: NbaRepository,
    @NbaPlayerId var nbaPlayerId: Int?,
    private val playerWithTeamToNbaPlayerUiMapper: PlayerWithTeamToNbaPlayerUiMapper
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NbaDetailsViewModel(
            nbaRepository = nbaRepository,
            nbaPlayerId = nbaPlayerId,
            playerWithTeamToNbaPlayerUiMapper = playerWithTeamToNbaPlayerUiMapper
        ) as T
    }
}


