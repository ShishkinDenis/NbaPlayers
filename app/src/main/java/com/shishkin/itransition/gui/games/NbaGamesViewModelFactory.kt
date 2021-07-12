package com.shishkin.itransition.gui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject

class NbaGamesViewModelFactory @Inject constructor(private val nbaRepository: NbaRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NbaGamesViewModel(this.nbaRepository) as T
    }
}