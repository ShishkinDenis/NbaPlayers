package com.shishkin.itransition.gui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.gui.nba.NbaViewModel
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject

class NbaGamesViewModelFactory  @Inject constructor(var nbaRepository: NbaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NbaGamesViewModel::class.java)) {
            NbaGamesViewModel(this.nbaRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}