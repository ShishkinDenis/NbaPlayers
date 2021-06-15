package com.shishkin.itransition.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.gui.nba.NbaViewModel
import com.shishkin.itransition.gui.nbadetails.NbaDetailsViewModel
import com.shishkin.itransition.repository.NbaPlayerRepository
import javax.inject.Inject


class MyViewModelFactory @Inject constructor(var nbaPlayerRepository: NbaPlayerRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NbaViewModel::class.java!!) -> {
                NbaViewModel(this.nbaPlayerRepository) as T
            }
            modelClass.isAssignableFrom(NbaDetailsViewModel::class.java!!) -> {
                NbaDetailsViewModel(this.nbaPlayerRepository) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}