package com.shishkin.itransition.gui.nbadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject

class NbaDetailsViewModelFactory @Inject constructor(
    private val nbaRepository: NbaRepository,
    @NbaPlayerId var nbaPlayerId: Int?
) : ViewModelProvider.Factory {

  // TODO Evgeny см NbaViewModelFactory
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NbaDetailsViewModel::class.java)) {
            NbaDetailsViewModel(this.nbaRepository, this.nbaPlayerId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

