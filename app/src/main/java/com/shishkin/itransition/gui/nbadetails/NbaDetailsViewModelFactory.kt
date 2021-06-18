package com.shishkin.itransition.gui.nbadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.R
import com.shishkin.itransition.di.NbaPlayerId
import com.shishkin.itransition.repository.NbaPlayerRepository
import javax.inject.Inject


class NbaDetailsViewModelFactory @Inject constructor(
    var nbaPlayerRepository: NbaPlayerRepository,
    @NbaPlayerId var nbaPlayerId: Int?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NbaDetailsViewModel::class.java)) {
            NbaDetailsViewModel(this.nbaPlayerRepository, this.nbaPlayerId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

