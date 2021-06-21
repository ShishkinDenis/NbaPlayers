package com.shishkin.itransition.gui.nbadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.di.NbaPlayerId
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject


class NbaDetailsViewModelFactory @Inject constructor(
    var nbaRepository: NbaRepository,
    @NbaPlayerId var nbaPlayerId: Int?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NbaDetailsViewModel::class.java)) {
            NbaDetailsViewModel(this.nbaRepository, this.nbaPlayerId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

