package com.shishkin.itransition.gui.nbadetails

import androidx.lifecycle.ViewModel
import com.shishkin.itransition.repository.NbaPlayerRepository
import javax.inject.Inject

class NbaDetailsViewModel @Inject constructor(var nbaPlayerRepository: NbaPlayerRepository) :
    ViewModel() {

    //    TODO use Flow
    fun fetchSpecificNbaPlayer(playerId: Int) {
        nbaPlayerRepository.getSpecificPlayer(playerId)
    }

}