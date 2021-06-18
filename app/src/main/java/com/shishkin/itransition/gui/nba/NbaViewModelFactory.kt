package com.shishkin.itransition.gui.nba

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.repository.NbaPlayerRepository
import javax.inject.Inject

class NbaViewModelFactory @Inject constructor(var nbaPlayerRepository: NbaPlayerRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NbaViewModel::class.java)) {
            NbaViewModel(this.nbaPlayerRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}